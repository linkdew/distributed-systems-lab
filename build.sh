#!/usr/bin/env bash

cd service-comments/

docker build -t linkdew/service-comments:latest .
docker push linkdew/service-comments

cd ..
cd service-posts/

docker build -t linkdew/service-posts:latest .
docker push linkdew/service-posts

cd ..
cd service-rating/

docker build -t linkdew/service-rating:latest .
docker push linkdew/service-rating

cd ..
minikube stop && minikube delete
minikube start

cd service-comments/
kubectl apply -f workloads.yaml

cd ..
cd service-posts/
kubectl apply -f workloads.yaml

cd ..
cd service-rating/
kubectl apply -f workloads.yaml

echo "$(minikube ip)"
