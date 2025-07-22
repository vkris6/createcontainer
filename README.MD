### Docker-in-Docker buildah - Jenkins shared library

This module contains Shared library for Docker-in-Docker using buildah and this module will enable you to:

Create your own private buildah image and upload to gcp container registry.
Create buildah pod and create an nginx within the buildah pod and upload to gcp container registry.
Nginx deployment on GKE.


The same can be used on any cloud, only replace the cluster-information and Cloud authentication on buildah container.

To create any application on container, replace the nginx.yaml with any desired application.

### How do I get set up?

### Dependencies
* Already running GCP cluster.
* Jenkins 2.167 or any pipeline supported.
* kubectl
* gcloud
* add your authentication for GCP and git on buildah container, build and push to GCP or any cloud

### Create and push buildah container.

Configure kubectl command line access by running the following command:

```
gcloud container clusters get-credentials [CLUSTER_NAME] --zone [REGION_NAME] --project [PROJECT_ID]
```

Create kubernetes secret 
```
kubectl create secret docker-registry gcr-json-key --docker-server=eu.gcr.io --docker-username=_json_key --docker-password="$(cat ./key.json)" --docker-email=[EMAIL_ID]
```

Inside resources/aem_docker/buildah-tar/

```
docker build -t eu.gcr.io/[PROJECT_ID]/buildah .
```
```
docker push eu.gcr.io/[PROJECT_ID]/buildah
```
Configure Jenkins shared library and pipeline job 

```
@Library('createcontainer@master')_
node {
try{
    buildContainers {}
             }catch(Exception e)
{   
    println e
    throw e
}    
}
```

Once jobs is triggerred you should be able to see nginx latest uploaded to container registry. We can now deploy it using.

```
kubectl create -f resources/aem_docker/buildah/kubernetes/nginx.yaml
```

### Who do I talk to? ###

* vijaykrishnamurthy8@gmail.com

