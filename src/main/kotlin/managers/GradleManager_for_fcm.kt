package managers

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.EmptyAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.Document
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.ui.SelectFromListDialog
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VirtualFile
import org.apache.commons.lang.StringUtils
import util.Constants

import javax.swing.*
import java.io.FileNotFoundException
import java.util.stream.Stream

class GradleManager_for_fcm(private val project: Project) {

    private var buildGradle: Document? = null
    private var buildGradleproject: Document? = null
    private var modules = arrayOf<Any>()

    private var projectBaseDir: VirtualFile? = null
    private var gradle_for_fcm:Boolean=false
    private var playstore:Boolean=false
    private var install_referrer:Boolean=false
    private var apply_plugin:Boolean=false
    private var classpath:Boolean=false

    @Throws(FileNotFoundException::class)
    fun initBuildGradle(): Boolean {
        checkFilesExist()

        val toStringAspect = SelectFromListDialog.ToStringAspect { it.toString() }
        val gradleVirtualFile: VirtualFile?
        val gradleVirtualFilenew: VirtualFile?
        if (modules.size > 1) {
            val dialog = SelectFromListDialog(
                project,
                modules,
                toStringAspect,
                "Select Gradle Module",
                ListSelectionModel.SINGLE_SELECTION
            )
            val isOk = dialog.showAndGet()
            if (isOk) {
                gradleVirtualFile = projectBaseDir!!
                    .findChild(dialog.selection[0].toString())!!
                    .findChild("build.gradle")
            } else {
                return false
            }
        } else if (modules.size == 1) {
            gradleVirtualFile = projectBaseDir!!
                .findChild(modules[0] as String)!!
                .findChild("build.gradle")
        } else {
            gradleVirtualFile = projectBaseDir!!
                .findChild("build.gradle")
        }
        gradleVirtualFilenew = projectBaseDir!!.findChild("build.gradle")
        if (gradleVirtualFile != null) {
            buildGradleproject= FileDocumentManager.getInstance().getDocument(gradleVirtualFilenew!!)
            buildGradle = FileDocumentManager.getInstance().getDocument(gradleVirtualFile)
        }

        return true
    }

    @Throws(FileNotFoundException::class)
    private fun checkFilesExist() {

        val basePath = project.basePath
        if (StringUtils.isEmpty(basePath)) {
            throw FileNotFoundException(Constants.ERROR_BASE_PATH_NOT_FOUND)
        }

        projectBaseDir = LocalFileSystem.getInstance().findFileByPath(basePath!!)
        if (projectBaseDir == null) {
            throw FileNotFoundException(Constants.ERROR_PROJECT_BASE_DIR_NOT_FOUND)
        }

        val virtualSettingsGradle = projectBaseDir!!.findChild("settings.gradle")
        if (virtualSettingsGradle != null) {
            val settingsGradle = FileDocumentManager.getInstance().getDocument(virtualSettingsGradle)
            if (settingsGradle != null) {
                modules = readSettingsGradle(settingsGradle)
            }
        } else if (projectBaseDir!!.findChild("build.gradle") == null) {
            throw FileNotFoundException(Constants.ERROR_BUILD_GRADLE_NOT_FOUND)
        }
    }


    fun checkbeforeinsertion()
    {
        val documentText = buildGradle!!.text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val documentTextnew=buildGradleproject!!.text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        // val sb = StringBuilder()
        for (i in documentText.indices)
        {
            var line = documentText[i]
            if(line.contains("com.google.firebase:firebase-messaging:"))
            {
                gradle_for_fcm=true
            }
            if(line.contains("com.google.android.gms:play-services-ads:"))
            {
                playstore=true
            }
            if(line.contains("com.android.installreferrer:installreferrer:"))
            {
                install_referrer=true
            }
            if(line.contains("apply plugin: 'com.google.gms.google-services'"))
            {
                apply_plugin=true
            }
            if(line.contains("classpath 'com.google.gms:google-services:"))
            {
                classpath=true
            }
        }
        for (i in documentTextnew.indices)
        {
            var line = documentTextnew[i]
            if(line.contains("classpath 'com.google.gms:google-services:"))
            {
                classpath=true
            }
        }

    }
    fun addDependency(repository: String, actionEvent: AnActionEvent) {
        checkbeforeinsertion()
        val documentText = buildGradle!!.text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val documentTextnew=buildGradleproject!!.text.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val sb = StringBuilder()
        val sb2= StringBuilder()
        for (i in documentText.indices) {
            val line = documentText[i]

            sb
                .append(line)
                .append("\n")
            if(gradle_for_fcm==false && install_referrer==false) {
                if (line.contains(Constants.DEPENDENCIES)) {
                    if (line.contains("{")) {
                        sb
                            .append("\t${Constants.IMPLEMENTATION} '")
                            .append(repository)
                            .append("'\n")
//                            .append("\t${Constants.IMPLEMENTATION} '")
//                            .append(Constants.PLAYSTORE)
                           // .append("'\n")
                            .append("\t${Constants.IMPLEMENTATION} '")
                            .append(Constants.Intalrefrerrer)
                            .append("'\n")
                        gradle_for_fcm= true
                        playstore=true
                        install_referrer=true
                    }
                }
            }
            if(gradle_for_fcm==false ) {
                if (line.contains(Constants.DEPENDENCIES)) {
                    if (line.contains("{")) {
                        sb
                            .append("\t${Constants.IMPLEMENTATION} '")
                            .append(repository)
                            .append("'\n")
                    }
                }
            }
//            if(playstore==false) {
//                if (line.contains(Constants.DEPENDENCIES)) {
//                    if (line.contains("{")) {
//                        sb
//                            .append("\t${Constants.IMPLEMENTATION} '")
//                            .append(Constants.PLAYSTORE)
//                            .append("'\n")
//                    }
//                }
//            }
            if(install_referrer==false) {
                if (line.contains(Constants.DEPENDENCIES)) {
                    if (line.contains("{")) {
                        sb
                            .append("\t${Constants.IMPLEMENTATION} '")
                            .append(Constants.Intalrefrerrer)
                            .append("'\n")
                    }
                }
            }
            if(apply_plugin==false) {
                if (i == documentText.lastIndex) {
                    sb.append("apply plugin: 'com.google.gms.google-services'")
                }
            }
        }

        for (i in documentTextnew.indices) {
            val line = documentTextnew[i]

            sb2
                .append(line)
                .append("\n")
            if(classpath==false) {
                if (line.contains(Constants.DEPENDENCIES)) {
                    if (line.contains("{")) {
                        sb2
                            .append("        classpath 'com.google.gms:google-services:4.3.3'")
                            .append("\n")

                    }
                }
            }
        }
        writeToProjectGradle(sb2,actionEvent)
        writeToGradle(sb, actionEvent)
    }

    private fun writeToGradle(stringBuilder: StringBuilder, actionEvent: AnActionEvent) {
        val application = ApplicationManager.getApplication()
        application.invokeLater {
            application.runWriteAction { buildGradle!!.setText(stringBuilder) }
            syncProject(actionEvent)
        }
    }
    private fun writeToProjectGradle(stringBuilder: StringBuilder, actionEvent: AnActionEvent) {
        val application = ApplicationManager.getApplication()
        application.invokeLater {
            application.runWriteAction { buildGradleproject!!.setText(stringBuilder) }
            syncProject(actionEvent)
        }
    }
    // TODO do not allow this method called without invokeLater()
    private fun syncProject(actionEvent: AnActionEvent) {
        val androidSyncAction = getAction("Android.SyncProject")
        val refreshAllProjectAction = getAction("ExternalSystem.RefreshAllProjects")

        if (androidSyncAction != null && androidSyncAction !is EmptyAction) {
            androidSyncAction.actionPerformed(actionEvent)
        } else if (refreshAllProjectAction != null && refreshAllProjectAction !is EmptyAction) {
            refreshAllProjectAction.actionPerformed(actionEvent)
        } else {
            SwingUtilities.invokeLater {
                Messages.showInfoMessage(
                    Constants.ERROR_SYNC_FAILED,
                    Constants.ERROR_TITLE_SYNC_FAILED
                )
            }
        }
    }

    private fun getAction(actionId: String): AnAction? {
        return ActionManager.getInstance().getAction(actionId)
    }

    private fun readSettingsGradle(settingsGradle: Document): Array<Any> {
        return Stream.of(*settingsGradle.text.split("'".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            .filter { s -> s.startsWith(":") }
            .map { s -> s.replace(":", "") }
            .toArray()
    }
}
