# Deploy Keycloak to AWS EKS

Apart of standard Kubernetes tools like `kubectl` and `helm` below example uses [eksctl](https://eksctl.io) to automate provisioning of the AWS infrastructure.

Here are the tools and versions I used:

```bash
$ aws --version
aws-cli/2.8.3 Python/3.9.11 Linux/4.14.291-218.527.amzn2.x86_64 exec-env/CloudShell exe/x86_64.amzn.2 prompt/off
$ eksctl version
0.115.0
$ helm version
version.BuildInfo{Version:"v3.10.1", GitCommit:"9f88ccb6aee40b9a0535fcc7efea6055e1ef72c9", GitTreeState:"clean", GoVersion:"go1.18.7"}
$ kubectl version
Client Version: version.Info{Major:"1", Minor:"23+", GitVersion:"v1.23.7-eks-4721010", GitCommit:"b77d9473a02fbfa834afa67d677fd12d690b195f", GitTreeState:"clean", BuildDate:"2022-06-27T22:22:16Z", GoVersion:"go1.17.10", Compiler:"gc", Platform:"linux/amd64"}
Server Version: version.Info{Major:"1", Minor:"23+", GitVersion:"v1.23.10-eks-15b7512", GitCommit:"cd6399691d9b1fed9ec20c9c5e82f5993c3f42cb", GitTreeState:"clean", BuildDate:"2022-08-31T19:17:01Z", GoVersion:"go1.17.13", Compiler:"gc", Platform:"linux/amd64"}
```

## Setup
Setup env variables for AWS account and region:
```bash
export AWS_ACCOUNT=$(aws sts get-caller-identity --query 'Account' --output text)
export AWS_REGION=ap-northeast-2
export CLUSTER_EKS_NAME=spring-demo
export CLUSTER_CERT_ARN=arn:aws:acm:${AWS_REGION}:${AWS_ACCOUNT}:certificate/95aadb9b-a05c-477d-9d73-7a89329e4af6
```
## Deploy and setup infrastructure

Deploy and setup EKS cluster:

```bash
# install gettext utils (envsubst is used below)
sudo yum install gettext

# create cluster
eksctl create cluster \
  --name $CLUSTER_EKS_NAME \
  --region $AWS_REGION \
  --version 1.23 \
  --with-oidc \
  --node-type t3.medium \
  --nodes 4 \
  --managed

# create ALB controller
curl -o aws-load-balancer-controller-policy.json https://raw.githubusercontent.com/kubernetes-sigs/aws-load-balancer-controller/v2.4.4/docs/install/iam_policy.json
aws iam create-policy \
  --policy-name AmazonEKS_AWSLoadBalancerControllerIAMPolicy \
  --policy-document file://aws-load-balancer-controller-policy.json
eksctl create iamserviceaccount \
  --cluster=$CLUSTER_EKS_NAME \
  --region $AWS_REGION \
  --namespace=kube-system \
  --name=aws-load-balancer-controller \
  --attach-policy-arn=arn:aws:iam::$AWS_ACCOUNT:policy/AmazonEKS_AWSLoadBalancerControllerIAMPolicy \
  --approve
helm repo add eks https://aws.github.io/eks-charts
helm install aws-load-balancer-controller eks/aws-load-balancer-controller \
  -n kube-system \
  --set clusterName=$CLUSTER_EKS_NAME \
  --set serviceAccount.create=false \
  --set serviceAccount.name=aws-load-balancer-controller



# create CSI ESB driver which is required for PVC which are used by Bitnami PostgreSQL chart
eksctl create iamserviceaccount \
  --cluster $CLUSTER_EKS_NAME \
  --region $AWS_REGION \
  --name ebs-csi-controller-sa \
  --namespace kube-system \
  --attach-policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy \
  --approve \
  --role-only \
  --role-name AmazonEKS_EBS_CSI_DriverRole
eksctl create addon \
  --cluster $CLUSTER_EKS_NAME \
  --region $AWS_REGION \
  --name aws-ebs-csi-driver \
  --service-account-role-arn arn:aws:iam::$AWS_ACCOUNT:role/AmazonEKS_EBS_CSI_DriverRole

# add help repo
helm repo add codecentric https://codecentric.github.io/helm-charts

# install keycloak thourgh helm
helm install keycloak codecentric/keycloak --set keycloak.username=admin --set keycloak.password=12345678


# get keycloak running service 
kubectl get svc

# port forward keycloak to local 
kubectl port-forward svc/kubernetes 8080:8080 

#expected result should look like this:
Forwarding from 127.0.0.1:8080 -> 8080
Forwarding from [::1]:8080 -> 8080
Handling connection for 8080
Handling connection for 8080
```



## Clean up resources:

```bash
eksctl delete cluster --name $CLUSTER_EKS_NAME --region $AWS_REGION
```
