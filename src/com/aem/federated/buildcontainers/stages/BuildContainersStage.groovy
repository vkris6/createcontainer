package com.aem.federated.buildcontainers.stages
def dockerContainerBuild() {

	def tasks = [:]

	tasks["Creating Dispatcher Container"] = {
		stage ("Creating Dispatcher Container"){

			dir("${env.JENKINS_HOME}/jobs/${env.JOB_NAME}/builds/${env.BUILD_NUMBER}/libs/createcontainer/resources/aem_docker/buildah/kubernetes") {
				String filenew_dispatcher = readFile('buildah-dispatcher.yaml')
				dispatcherInitiateBuildah = sh(returnStdout: true, script: "kubectl create -f buildah-dispatcher.yaml").toString().trim()
				println("dispatcherInitiateBuildah is ${dispatcherInitiateBuildah}")

				def dispatcherContainerStatus = "dummy"
				while ( !dispatcherContainerStatus.toString().equals("Completed") && !dispatcherContainerStatus.toString().equals("Error") ) {
					try{
						dispatcherContainerStatus = sh(returnStdout: true, script: "kubectl get po -a | grep buildah | awk {'print \$3'}").toString().trim()
						println("dispatcherContainerStatus is ${dispatcherContainerStatus}")
						sleep 5
					}catch(Exception e){}
				}
				if ( dispatcherContainerStatus == "Completed" )
				{
					dispatcherCompletedBuildah = sh(returnStdout: true, script: "kubectl delete -f buildah-dispatcher.yaml").toString().trim()
					println("dispatcherCompletedBuildah is ${dispatcherCompletedBuildah}")
					println("dispatcherContainerStatus is ${dispatcherContainerStatus}. Successfully created and uploaded Dispatcher Container")
				}
				else if ( dispatcherContainerStatus == "Error" )
				{
					println("dispatcherContainerStatus is ${dispatcherContainerStatus} . Please check containers for error and fix")
					throw new Exception ("dispatcherContainerStatus is ${dispatcherContainerStatus} . Please check containers for error and fix")
				}
				else
				{
					println("dispatcherContainerStatus is ${dispatcherContainerStatus}")
				}
			}
		}
	}
	parallel tasks
}