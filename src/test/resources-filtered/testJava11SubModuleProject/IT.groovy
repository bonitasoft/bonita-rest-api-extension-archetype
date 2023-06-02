import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

// Run 'mvn install' fisrt and then 'mvn groovy:execute -Dsource=target/test-classes/testJava11SubModuleProject/IT.groovy -Dscope=test' from project root

// Given
def sourcePath = '${project.basedir}/src/test/resources/testJava11SubModuleProject/'
def testPath = '${project.build.testOutputDirectory}/testJava11SubModuleProject/'
def sourceParentFolder = "${sourcePath}/module-parent"
def parentFolder = "${testPath}/module-parent"
def moduleArtifactId = "my-rest-api"


println "[Integration Test] Test generation of sub module ${moduleArtifactId} in folder ${parentFolder}"

// Delete previous run if any
def moduleFolder = new File("${parentFolder}/${moduleArtifactId}")
if (moduleFolder.exists()) {
	Files.deleteIfExists(Paths.get("${parentFolder}/${moduleArtifactId}/pom.xml"))
	moduleFolder.deleteDir()
	// Reset the parent pom (whitout sub-module declaration)
	Files.copy(Paths.get("${sourceParentFolder}/pom.xml"), Paths.get("${parentFolder}/pom.xml"), StandardCopyOption.REPLACE_EXISTING);
}

// When
println "Generate sub module ..."
def sout = new StringBuilder(), serr = new StringBuilder()
def proc = """mvn archetype:generate -B  \
    -DarchetypeGroupId=org.bonitasoft.archetypes \
    -DarchetypeArtifactId=bonita-rest-api-extension-archetype \
    -DarchetypeVersion=${project.version} \
    -DgroupId=org.company.api \
    -DartifactId=${moduleArtifactId} \
    -Dversion=0.0.1-SNAPSHOT \
    -Dlanguage=java \
    -DbonitaVersion=7.15.0 \
    -DapiName=myRestApi \
    -DapiDisplayName=My-REST-API \
    -DpathTemplate=my-rest-api \
    -DurlParameters=p,c \
    -DhttpVerb=GET
""".execute(null, new File(parentFolder))
proc.consumeProcessOutput(sout, serr)
proc.waitForOrKill(10 * 60 * 1000)
println "out> $sout\nerr> $serr"

// Then
println "Verifying generation result  ..."

assert proc.exitValue() == 0: "Maven archetype execution exit code should be 0"

def parentPomFile = new File("${parentFolder}/pom.xml")
assert parentPomFile.text.contains("<module>${moduleArtifactId}</module>"): 'Parent pom should declare project as sub module'

def modulePomFile = new File("${parentFolder}/${moduleArtifactId}/pom.xml")
def referencePomFile = new File("${testPath}/reference/pom.xml")
assert referencePomFile.text == modulePomFile.text: 'Reference pom and project pom should have the same content'

println "SUCCESS"


