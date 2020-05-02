package thermos

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.*

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

import java.util.regex.Matcher

class VersionSetter extends DefaultTask {
    @Input
    String forgeBuild

    @InputFile
    File forgeVersionFile
	
	@InputFile
	File forgeModLoaderVersionFile

    @TaskAction
    def setVersion() {
		println "Setting forge version whoop!"
	
        def forgeVersion = forgeVersionFile.text
        def int majorVersion = v(forgeVersion =~ /.+int majorVersion\s+=\s+(\d+);/)
        def int minorVersion = v(forgeVersion =~ /.+int minorVersion\s+=\s+(\d+);/)
        def int revisionVersion = v(forgeVersion =~ /.+int revisionVersion\s+=\s+(\d+);/)
        def int buildVersion = forgeBuild.toInteger()	

		new File('src/main/resources/', 'fmlversion.properties').withWriter('utf-8') { 
			it << '''\
fmlbuild.major.number=''' 
it << majorVersion.toString()
it << '''
fmlbuild.minor.number='''
it << minorVersion.toString()
it << '''
fmlbuild.revision.number='''
it << revisionVersion.toString()
it << '''
fmlbuild.build.number='''
it << buildVersion.toString()
it << '''
fmlbuild.mcversion=1.7.10
fmlbuild.mcpversion=9.08
'''
		}
    }
	
    private static final class NopOutputStream extends OutputStream {
        @Override
        void write(byte[] b, int off, int len) throws IOException {
        }

        @Override
        void write(byte[] b) throws IOException {
        }

        @Override
        void write(int b) throws IOException {
        }
    }
	
    static int v(Matcher matcher) {
        matcher.find()
        matcher.group(1) as int
    }
}
