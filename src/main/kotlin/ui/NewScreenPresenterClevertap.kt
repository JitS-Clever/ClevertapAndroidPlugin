package ui

import com.intellij.credentialStore.SERVICE_NAME_PREFIX
import com.intellij.notification.NotificationDisplayType
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.util.NotNullLazyValue
import data.file.CurrentPath
import data.file.FileCreator
import data.file.PackageExtractor
import data.file.WriteActionDispatcher
import data.repository.ModuleRepository
import managers.*



import org.jetbrains.kotlin.idea.kdoc.insert



import util.Constants
import util.Methods
import java.io.FileNotFoundException

class NewScreenPresenterClevertap(
    private val view: NewScreenView,
    private val fileCreator: FileCreator,
    private val packageExtractor: PackageExtractor,
    private val writeActionDispatcher: WriteActionDispatcher,
    private val moduleRepository: ModuleRepository,
    private val currentPath: CurrentPath?
) {

    companion object {
        private val NOTIFICATION_GROUP = object :
            NotNullLazyValue<NotificationGroup>() {
            override fun compute(): NotificationGroup {
                return NotificationGroup(
                    Constants.DISPLAY_ID,
                    NotificationDisplayType.BALLOON,
                    true
                )
            }
        }
    }

    private var gradleManager: GradleManager? = null
    private var manifestManager: ManifestManager? = null
    private var applicationClassManager:ApplicationClassManager?=null
    private var abc:abc?=null
    private var AuditreportManager:AuditreportManager?=null



    fun onLoadView() {
        view.showPackage(packageExtractor.extractFromCurrentPath())
        view.showModules(moduleRepository.getAllModules())
        currentPath?.let { view.selectModule(currentPath.module) }

    }

    fun onOkClick(
        event: AnActionEvent,
        packageName: String,
        Account_id: String,
        Account_token: String,
        //use_google_ad_id: String,
        use_google_ad_id_rb1 : Boolean,
        use_google_ad_id_rb2: Boolean,
        Exclude_filesText :String,
        contentTextText: String,
        applicationclassname: String,
        IsRadiobuttonrb1Selected: Boolean,
        dependencyVersionText: String,
        isNeedReadMeForInstructions: Boolean,
        moduleName: String
        //launchingactivityname:String
    ) {
//        var panelforapproval=CleverTapInputApproval()
//        panelforapproval.manifest_content.setText("<html>"+"Permissions :"+"<br>"+
//                "&lt uses-permission android:name=\"android.permission.INTERNET\" &gt"+"<br>"+
//                "&lt uses-permission android:name=\"android.permission.ACCESS_NETWORK_STATE\" &gt"+"<br>"+
//                "<br>"+
//                "CleverTapCredentials :"+"<br>"+
//                "&lt meta-data"+"<br>"+
//                "        android:name=\"CLEVERTAP_ACCOUNT_ID\""+"<br>"+
//                "        android:value=\"$serviceNameText \" / &gt"+"<br>"+
//                "&lt meta-data"+"<br>"+
//                "        android:name=\"CLEVERTAP_TOKEN\""+"<br>"+
//                "        android:value=\" \" /&gt"+"<br>"+
//                "&lt meta-data"+"<br>"+
//                "        android:name=\"CLEVERTAP_USE_GOOGLE_AD_ID\""+"<br>"+
//                "        android:value=\" \" /&gt"+"<br>"+
//                "<br>"+
//                // "}"+
//                "</html>")


        event.project?.let { project ->


            //

            // CleverTapInputApproval().manifest_content.setText("abc")

            CTInputApprovalDialog(event,Account_id,Account_token,use_google_ad_id_rb1,use_google_ad_id_rb2,Exclude_filesText,applicationclassname,IsRadiobuttonrb1Selected).show()

            writeActionDispatcher.dispatch {
         /*       fileCreator.createScreenFiles(
                    packageName,
                    serviceNameText,
                    pendingIntentText,
                    contentTitleText,
                    contentTextText,
                    isNeedReadMeForInstructions,
                    moduleName,
                    Methods.checkPrimaryColorInColorsFile(project)
                )*/
            }
//            gradleManager = GradleManager(project)
//            manifestManager = ManifestManager(project)
//            applicationClassManager=ApplicationClassManager(project)
//            abc=abc(project)
//            AuditreportManager=AuditreportManager(project)


            try {
//**//                abc?.let{
//                    //it.AndroidManifest()
//                   // if(it.initapplicationclass()){
//                    it.initapplicationclass()
//                    //it.checkinsertion()
//                }
//                AuditreportManager?.let{
//
//                    it.initapplicationclass()
//
//                }
                //}
//**//                gradleManager?.let {
//                    if (it.initBuildGradle()) {
//                        it.addDependency("com.clevertap.android:clevertap-android-sdk:4.0.2", event)
//
//                    }
//                }
//**//                if(IsRadiobuttonrb1Selected==true) {
//                    applicationClassManager?.let {
//                        it.initapplicationclass(applicationclassname, IsRadiobuttonrb1Selected)
//                    }
//                }

                       // it.initiateclevertap(
                            //Methods.addApplication(applicationclassname, IsRadiobuttonrb1Selected)
                       // )

                   // }
               // }



//                    if(it.AndroidManifest()){
//                        it.getpackagename()
//                    }
 //               }


//final
//**//                manifestManager?.let {
//                    if(it.initAndroidManifest()){
//                        it.addData( Methods.getPermissionContent(),Methods.addApplicationClass(applicationclassname,IsRadiobuttonrb1Selected),
//                            Methods.getAndroidManifestContent(
//                            packageName,
//                            serviceNameText,
//                            pendingIntentText,
//                            contentTitleText,
//                            contentTextText,applicationclassname
//                        ),serviceNameText,pendingIntentText,contentTitleText,Exclude_filesText
//
//                            )
//                    }
//
//                }


//                       manifestManager?.let {
//
//                    if (it.initAndroidManifest()) {
//
//
//
//                        it.addMetaDataContent(
//                            Methods.getAndroidManifestContent(
//                                packageName,
//                                serviceNameText,
//                                pendingIntentText,
//                                contentTitleText,
//                                Exclude_filesText,
//                                contentTextText
//                            )
//                        )
//
//
//                    }
//
//
//
//                }

//                manifestManager?.let {
//
//                    if (it.initAndroidManifest()) {
//                          it.launchingactname()
////                        it.addPermissionData(
////                            Methods.getPermissionContent()
////                        )
////                        it.addApplicationClass(
////                            Methods.addApplicationClass(applicationclassname,IsRadiobuttonrb1Selected)
////                        )
//                    }
//                }





//                ApplicationManager.getApplication()
//                    .invokeLater({
//                        Notifications.Bus.notify(
//                            NOTIFICATION_GROUP.value
//                                .createNotification(
//                                    Constants.NOTIFICATION_TITLE,
//                                    Constants.NOTIFICATION_CONTENT,
//                                    NotificationType.INFORMATION,
//                                    null
//                                )
//                        )
//                    }, ModalityState.NON_MODAL)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        view.close()
    }
}