import com.aem.federated.buildcontainers.stages.*

def call(body) {
	stage("buildContainersStage")
	{
	def buildContainersStage = new BuildContainersStage();
	buildContainersStage.dockerContainerBuild()
	}
}
