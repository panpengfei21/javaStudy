#!/bin/bash

# 关闭防火墙
echo '========>关闭防火墙'
systemctl stop firewalld 
systemctl disable firewalld

#关闭selinux
echo "========>关闭selinux"
setenforce 0
sed -i 's/enforcing/disabled/' /etc/selinux/config

#关闭swap
echo "========>关闭swap"
swapoff -a
sed -ri 's/.*swap.*/#&/' /etc/fstab


#将桥接的 IPv4 流量传递到 iptables 的链
echo "========>将桥接的 IPv4 流量传递到 iptables 的链"
cat > /etc/sysctl.d/k8s.conf << EOF 
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1 
EOF

sysctl --system

#时间同步
echo ''
echo '========>时间同步'
yum install ntpdate -y 
ntpdate time.windows.com

#安装docker
echo ''
echo '========>安装docker'
wget https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo -O /etc/yum.repos.d/docker-ce.repo

yum -y install docker-ce-18.06.1.ce-3.el7
systemctl enable docker && systemctl start docker

cat > /etc/docker/daemon.json << EOF
{
  "registry-mirrors": ["https://b9pmyelo.mirror.aliyuncs.com"] 
}
EOF

systemctl restart docker

echo ''
echo "======>安装阿里云的yum源"
cat > /etc/yum.repos.d/kubernetes.repo << EOF 
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64
enabled=1
gpgcheck=0
repo_gpgcheck=0
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF

echo ""
echo "======>安装kuberneters"
yum install -y kubelet-1.18.0 kubeadm-1.18.0 kubectl-1.18.0
systemctl enable kubelet

