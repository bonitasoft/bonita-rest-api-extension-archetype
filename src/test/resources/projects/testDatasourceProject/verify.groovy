import java.io.*
import java.util.zip.ZipFile
import java.util.Properties

def archive = context.projectDir.toPath()
        .resolve("target")
        .resolve("ds-rest-api-1.0.0-SNAPSHOT.zip")
        .toFile()

assert archive.exists() : "$archive should have been built"

def zf = new ZipFile(archive)
def pomFileEntry = zf.entries().find { it.name == 'META-INF/maven/com.company.bonitasoft/ds-rest-api/pom.xml' }

assert pomFileEntry != null : 'pom.xml file not found in META-INF'

def pomPropertiesEntry = zf.entries().find { it.name == 'META-INF/maven/com.company.bonitasoft/ds-rest-api/pom.properties' }
assert pomPropertiesEntry != null : 'pom.properties file not found in META-INF'

def properties = new Properties()
properties.load(zf.getInputStream(pomPropertiesEntry))
assert properties.groupId == 'com.company.bonitasoft' : 'Invalid groupId property'
assert properties.artifactId == 'ds-rest-api' : 'Invalid artifactId property'
assert properties.version == '1.0.0-SNAPSHOT' : 'Invalid version property'
