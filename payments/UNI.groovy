package ru.sberbank.payments

import groovy.xml.*

public class UNI extends Object implements Serializable {
    private static def Report(def text) {
        def result = ""
        def items = new HashMap<Object, ArrayList<Object>>()
        def xml = new XmlSlurper().parseText(text.toString())

        for (def item in xml."error"."*".toList()) {
            if ((!(items.containsKey(item.name())))) {
                items.put(item.name(), new ArrayList<Object>())
            }
            items.get(item.name()).add("${item.@name} : ${item.status.text()}")
        }

        if((!(items.isEmpty()))) {
            result += "<html>"
            result += "<style type='text/css'>"
            result += ".treeview { padding: 0; clear: both; font-family: Arial, sans-serif; width: 100%; }"
            result += ".treeview * { font-size: 100.1%; }"
            result += ".treeview ul{overflow: hidden; width: 100%; margin: 0; padding: 0 0 1.5em 0; list-style-type: none;}"
            result += ".treeview ul ul { overflow: visible; width: auto; margin: 0 0 0 0; padding: 0 0 0 0.75em; }"
            result += ".treeview ul.l { border-left: 1px solid; margin-left: -1px; }"
            result += ".treeview li.cl ul { display: none; }"
            result += ".treeview li { margin: 0; padding: 0; }"
            result += ".treeview li li { margin: 0 0 0 0.5em; border-left: 1px dotted; padding: 0; }"
            result += ".treeview li div { position: relative; height: 1.5em; min-height: 16px; //height: 1.3em; }"
            result += ".treeview li li div { border-bottom: 1px dotted; }"
            result += ".treeview li p{position: absolute; z-index: 1; top: 0.8em; //top: 0.65em; left: 1.75em; width: 100%; margin: 0; border-bottom: 1px dashed; padding: 0; }"
            result += ".treeview a { padding: 0.1em 0.2em; white-space: nowrap; //height: 1px; }"
            result += ".treeview a.files{position: absolute; top: 0.06em; margin-left: -1em; padding: 0; text-decoration: none; }"
            result += ".treeview li p { background: #f5f5ea; }"
            result += ".treeview .files { background: #f5f5ea; }"
            result += ".treeview ul.l { border-color: #f5f5ea; }"
            result += ".treeview li p { border-color: #f5f5ea; }"
            result += ".treeview ul li li { border-color: #999999; }"
            result += ".treeview ul li li div { border-color: #999999; }"
            result += ".treeview a { color: #000000; }"
            result += ".treeview a.files { color: #000000; }"
            result += ".treeview a.files:hover { color: #000000; }"
            result += ".treeview a:hover { color: #cc0000; }"
            result += "body { background: #f5f5ea; }"
            result += "</style>"
            result += "<script type='text/javascript'>"
            result += "function UnHide( eThis ) {"
            result += "if( eThis.innerHTML.charCodeAt(0) == 9658 ) {"
            result += "eThis.innerHTML = '&#9660;';"
            result += "eThis.parentNode.parentNode.parentNode.className = '';"
            result += "} else {"
            result += "eThis.innerHTML = '&#9658;';"
            result += "eThis.parentNode.parentNode.parentNode.className = 'cl';"
            result += "}"
            result += "return false;"
            result += "}"
            result += "</script>"
            result += "<body>"
            for (def type in items.keySet().sort()) {
                result += "<div class='treeview'>"
                result += "<ul>"
                result += "<li>"
                result += "<div>"
                result += "<p><a href='#' class='files' onclick='return UnHide(this)'>&#9660;</a>${type}</p>"
                result += "</div>"
                result += "<ul>"
                for (def status in items.get(type).sort()) {
                    result += "<div>"
                    result += "<p>${status}</p>"
                    result += "</div>"
                }
                result += "</ul>"
                result += "</li>"
                result += "</ul>"
                result += "</div>"
            }
            result += "</body>"
            result += "</html>"
        }

        return (result)
    }

    public static def PlayMQ(def wfs, def inventory, def playbook) {
        wfs.stage("ANSIBLE run MQ Playbook") {
            wfs.dir("${wfs.env.WORKSPACE}/git") {
                wfs.deleteDir()
                wfs.git(url: "${wfs.env.MQ_VARS_URL}", branch: "${wfs.env.MQ_VARS_BRANCH}", credentialsId: "${wfs.env.MQ_VARS_CREDENTIALS}")
                wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/mq")])
                wfs.fileOperations([wfs.folderCopyOperation(destinationFolderPath: "${wfs.env.WORKSPACE}/mq", sourceFolderPath: "${wfs.env.WORKSPACE}/git/${wfs.env.MQ_VARS_FOLDER}")])
                wfs.deleteDir()
                wfs.git(url: "${wfs.env.MQ_SCRIPTS_URL}", branch: "${wfs.env.MQ_SCRIPTS_BRANCH}", credentialsId: "${wfs.env.MQ_SCRIPTS_CREDENTIALS}")
                wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/mq")])
                wfs.fileOperations([wfs.folderCopyOperation(destinationFolderPath: "${wfs.env.WORKSPACE}/mq", sourceFolderPath: "${wfs.env.WORKSPACE}/git/${wfs.env.MQ_SCRIPTS_FOLDER}")])
                wfs.deleteDir()
                wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/git")])
                wfs.ansiblePlaybook(installation: "ansible26", inventory: "${wfs.env.WORKSPACE}/mq/${inventory}", playbook: "${wfs.env.WORKSPACE}/mq/${playbook}", vaultCredentialsId: "${wfs.env.MQ_VARS_VAULT}", sudoUser: null, forks: 5)
                wfs.deleteDir()
            }
        }

        return (null)
    }

    public static def PlayDP(def wfs, def inventory, def playbook) {
        wfs.stage("ANSIBLE run DP Playbook") {
            wfs.dir("${wfs.env.WORKSPACE}/git") {
                wfs.deleteDir()
                wfs.git(url: "${wfs.env.DP_VARS_URL}", branch: "${wfs.env.DP_VARS_BRANCH}", credentialsId: "${wfs.env.DP_VARS_CREDENTIALS}")
                wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/dp")])
                wfs.fileOperations([wfs.folderCopyOperation(destinationFolderPath: "${wfs.env.WORKSPACE}/dp", sourceFolderPath: "${wfs.env.WORKSPACE}/git/${wfs.env.DP_VARS_FOLDER}")])
                wfs.deleteDir()
                wfs.git(url: "${wfs.env.DP_SCRIPTS_URL}", branch: "${wfs.env.DP_SCRIPTS_BRANCH}", credentialsId: "${wfs.env.DP_SCRIPTS_CREDENTIALS}")
                wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/dp")])
                wfs.fileOperations([wfs.folderCopyOperation(destinationFolderPath: "${wfs.env.WORKSPACE}/dp", sourceFolderPath: "${wfs.env.WORKSPACE}/git/${wfs.env.DP_SCRIPTS_FOLDER}")])
                wfs.deleteDir()
                wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/git")])
                wfs.ansiblePlaybook(installation: "ansible26", inventory: "${wfs.env.WORKSPACE}/dp/${inventory}", playbook: "${wfs.env.WORKSPACE}/dp/${playbook}", vaultCredentialsId: "${wfs.env.DP_VARS_VAULT}", sudoUser: null, forks: 5)
                wfs.deleteDir()
            }
        }

        return (null)
    }

    public static def PlayNX(def wfs, def inventory, def playbook) {
        wfs.stage("ANSIBLE run NEXUS Playbook") {
            wfs.dir("${wfs.env.WORKSPACE}/git") {
                wfs.withCredentials([wfs.usernamePassword(credentialsId: "${wfs.env.NEXUS_CREDENTIALS}", usernameVariable: "NEXUS_USER", passwordVariable: "NEXUS_PASSWORD")]) {
                    wfs.deleteDir()
                    wfs.git(url: "${wfs.env.NEXUS_SCRIPTS_URL}", branch: "${wfs.env.NEXUS_SCRIPTS_BRANCH}", credentialsId: "${wfs.env.NEXUS_SCRIPTS_CREDENTIALS}")
                    wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/nexus")])
                    wfs.fileOperations([wfs.folderCopyOperation(destinationFolderPath: "${wfs.env.WORKSPACE}/nexus", sourceFolderPath: "${wfs.env.WORKSPACE}/git/${wfs.env.NEXUS_SCRIPTS_FOLDER}")])
                    wfs.deleteDir()
                    wfs.fileOperations([wfs.folderCreateOperation(folderPath: "${wfs.env.WORKSPACE}/distrib")])
                    wfs.ansiblePlaybook(installation: "ansible26", inventory: "${wfs.env.WORKSPACE}/nexus/${inventory}", playbook: "${wfs.env.WORKSPACE}/nexus/${playbook}", extraVars: [nexus_suffix: "${wfs.env.ENV}-${wfs.currentBuild.projectName}-${wfs.currentBuild.id}", nexus_file: "${wfs.env.WORKSPACE}/distrib.zip", nexus_distrib: "${wfs.env.WORKSPACE}/distrib", nexus_user: "${wfs.env.NEXUS_USER}", nexus_password: "${wfs.env.NEXUS_PASSWORD}", nexus_protocol: "${wfs.env.NEXUS_PROTOCOL}", nexus_host: "${wfs.env.NEXUS_HOST}", nexus_port: "${wfs.env.NEXUS_PORT}", nexus_repository: "${wfs.env.NEXUS_REPO}", nexus_group: "${wfs.env.NEXUS_GROUP}", nexus_artifact: "${wfs.env.NEXUS_ARTIFACT}", nexus_version: "${wfs.env.NEXUS_VERSION}", nexus_classifier: "${wfs.env.NEXUS_CLASSIFIER}"], sudoUser: null, forks: 5)
                    wfs.deleteDir()
                }
            }
        }

        return (null)
    }

    public static def Init(def wfs) {
        wfs.stage("Init") {
            wfs.dir("${wfs.env.WORKSPACE}") {
                wfs.deleteDir()
                if ((wfs.env.NEXUS_ARTIFACT == "")) {
                    wfs.env.NEXUS_ARTIFACT = wfs.env.DEPLOY_ARTIFACT
                }
                if ((wfs.env.NEXUS_VERSION == "")) {
                    wfs.env.NEXUS_VERSION = wfs.env.DEPLOY_VERSION
                }
                if ((wfs.env.NEXUS_ARTIFACT != null) && (wfs.env.NEXUS_VERSION != null)) {
                    wfs.currentBuild.displayName = "#${wfs.currentBuild.id} / ${wfs.currentBuild.projectName} / ${wfs.env.IGW} / ${wfs.env.ENV} / ${wfs.env.NEXUS_ARTIFACT} / ${wfs.env.NEXUS_VERSION} / ${wfs.env.NODE_NAME}"
                } else {
                    wfs.currentBuild.displayName = "#${wfs.currentBuild.id} / ${wfs.currentBuild.projectName} / ${wfs.env.IGW} / ${wfs.env.ENV} / ${wfs.env.NODE_NAME}"
                }
                wfs.currentBuild.result = "SUCCESS"
                wfs.echo("${wfs.env.NEXUS_PROTOCOL}://${wfs.env.NEXUS_HOST}:${wfs.env.NEXUS_PORT}/nexus/service/local/repositories/${wfs.env.NEXUS_REPO}/content/${wfs.env.NEXUS_GROUP}/${wfs.env.NEXUS_ARTIFACT}/${wfs.env.NEXUS_VERSION}/${wfs.env.NEXUS_ARTIFACT}-${wfs.env.NEXUS_VERSION}-${wfs.env.NEXUS_CLASSIFIER}.zip")
            }
        }

        return (null)
    }

    public static def Fail(def wfs) {
        wfs.stage("Fail") {
            wfs.dir("${wfs.env.WORKSPACE}") {
                wfs.currentBuild.result = "FAILURE"
                wfs.echo("${wfs.env.NEXUS_PROTOCOL}://${wfs.env.NEXUS_HOST}:${wfs.env.NEXUS_PORT}/nexus/service/local/repositories/${wfs.env.NEXUS_REPO}/content/${wfs.env.NEXUS_GROUP}/${wfs.env.NEXUS_ARTIFACT}/${wfs.env.NEXUS_VERSION}/${wfs.env.NEXUS_ARTIFACT}-${wfs.env.NEXUS_VERSION}-${wfs.env.NEXUS_CLASSIFIER}.zip")
            }
        }

        return (null)
    }

    public static def Notify(def wfs) {
        wfs.stage("Notify") {
            wfs.dir("${wfs.env.WORKSPACE}") {
                try {
                    wfs.withCredentials([wfs.usernamePassword(credentialsId: "${wfs.env.NEXUS_CREDENTIALS}", usernameVariable: "USERNAME", passwordVariable: "PASSWORD")]) {
                        wfs.fileOperations([wfs.fileDownloadOperation(url: "https://jira.ca.sbrf.ru/rest/api/latest/search?fields=attachment&jql=((key+in+(${wfs.env.NEXUS_VERSION.split('.JIRAKEY.')[-1]})))", userName: "${wfs.env.USERNAME}", password: "${wfs.env.PASSWORD}", targetLocation: "${wfs.env.WORKSPACE}", targetFileName: "${wfs.env.WORKSPACE}/search.json")])
                    }
                } catch (ex) {
                    wfs.fileOperations([wfs.fileCreateOperation(fileContent: "{ \"errorMessage\": \"${ex.message}\" }", fileName: "${wfs.env.WORKSPACE}/search.json")])
                }
                for (def file in wfs.findFiles(glob: "**/report.*.xml").toList()) {
                    if((!(UNI.Report(wfs.readFile(file: file.path.toString(), encoding: "UTF-8")).equals("")))) {
                        wfs.writeFile(file: "${file.name.toString()}.error.html", text: UNI.Report(wfs.readFile(file: file.path.toString(), encoding: "UTF-8")), encoding: "UTF-8")
                    }
                }
                for (def issue in wfs.readJSON(file: "${wfs.env.WORKSPACE}/search.json").get("issues")) {
                    for (def attachment in issue.get("fields").get("attachment")) {
                        wfs.env.MAIL_RECIPIENTS = "${wfs.env.MAIL_RECIPIENTS},${attachment.get("author").get("emailAddress")}"
                    }
                }
                if (wfs.currentBuild.result.toString() == "FAILURE") {
                    wfs.emailext(attachLog: true, compressLog: true, mimeType: "text/html", to: "${wfs.env.MAIL_RECIPIENTS}", subject: "${wfs.env.IGW} / ${wfs.env.ENV} / ${wfs.currentBuild.projectName} / ${wfs.currentBuild.id} / ${wfs.currentBuild.result}", body: "<?xml version='1.0' encoding='UTF-8'?><html><body><table style='border-top: 1px solid; border-bottom: 1px solid; border-spacing: 5pt; font-family: Calibri; font-size: 11pt; font-weight: lighter;'><tr><td style='width: 350; height: auto'>АС/ФП:</td><td style='width: 350; height: auto'>${wfs.env.IGW}</td></tr><tr><td style='width: 350; height: auto'>Среда:</td><td style='width: 350; height: auto'>${wfs.env.ENV}</td></tr><tr><td style='width: 350; height: auto'>Статус:</td><td style='width: 350; height: auto; color: red'>${wfs.currentBuild.result}</td></tr><tr><td style='width: 350; height: auto'>Инициатор:</td><td style='width: 350; height: auto'>${wfs.currentBuild.rawBuild.getCause(Cause.UserIdCause.class).getUserId()}</td></tr><tr><td style='width: 350; height: auto'>Задача:</td><td style='width: 350; height: auto'><a href='${wfs.currentBuild.absoluteUrl}'>Jenkins</a></td></tr><tr><td style='width: 350; height: auto'>NEXUS URL:</td><td style='width: 350; height: auto'><a href='${wfs.env.NEXUS_PROTOCOL}://${wfs.env.NEXUS_HOST}:${wfs.env.NEXUS_PORT}/nexus/service/local/artifact/maven/redirect?r=${wfs.env.NEXUS_REPO}&g=${wfs.env.NEXUS_GROUP}&a=${wfs.env.NEXUS_ARTIFACT}&v=${wfs.env.NEXUS_VERSION}&c=${wfs.env.NEXUS_CLASSIFIER}&p=zip'>Nexus</a></td></tr><tr><td style='width: 350; height: auto'>NEXUS Repo:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_REPO}</td></tr><tr><td style='width: 350; height: auto'>NEXUS Group:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_GROUP}</td></tr><tr><td style='width: 350; height: auto'>NEXUS Artifact:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_ARTIFACT}</td></tr><tr><td style='width: 350; height: auto'>NEXUS Version:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_VERSION}</td></tr></table></body></html>", attachmentsPattern: "**/*.html")
                }
                if (wfs.currentBuild.result.toString() == "SUCCESS") {
                    wfs.emailext(attachLog: true, compressLog: true, mimeType: "text/html", to: "${wfs.env.MAIL_RECIPIENTS}", subject: "${wfs.env.IGW} / ${wfs.env.ENV} / ${wfs.currentBuild.projectName} / ${wfs.currentBuild.id} / ${wfs.currentBuild.result}", body: "<?xml version='1.0' encoding='UTF-8'?><html><body><table style='border-top: 1px solid; border-bottom: 1px solid; border-spacing: 5pt; font-family: Calibri; font-size: 11pt; font-weight: lighter;'><tr><td style='width: 350; height: auto'>АС/ФП:</td><td style='width: 350; height: auto'>${wfs.env.IGW}</td></tr><tr><td style='width: 350; height: auto'>Среда:</td><td style='width: 350; height: auto'>${wfs.env.ENV}</td></tr><tr><td style='width: 350; height: auto'>Статус:</td><td style='width: 350; height: auto; color: green'>${wfs.currentBuild.result}</td></tr><tr><td style='width: 350; height: auto'>Инициатор:</td><td style='width: 350; height: auto'>${wfs.currentBuild.rawBuild.getCause(Cause.UserIdCause.class).getUserId()}</td></tr><tr><td style='width: 350; height: auto'>Задача:</td><td style='width: 350; height: auto'><a href='${wfs.currentBuild.absoluteUrl}'>Jenkins</a></td></tr><tr><td style='width: 350; height: auto'>NEXUS URL:</td><td style='width: 350; height: auto'><a href='${wfs.env.NEXUS_PROTOCOL}://${wfs.env.NEXUS_HOST}:${wfs.env.NEXUS_PORT}/nexus/service/local/artifact/maven/redirect?r=${wfs.env.NEXUS_REPO}&g=${wfs.env.NEXUS_GROUP}&a=${wfs.env.NEXUS_ARTIFACT}&v=${wfs.env.NEXUS_VERSION}&c=${wfs.env.NEXUS_CLASSIFIER}&p=zip'>Nexus</a></td></tr><tr><td style='width: 350; height: auto'>NEXUS Repo:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_REPO}</td></tr><tr><td style='width: 350; height: auto'>NEXUS Group:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_GROUP}</td></tr><tr><td style='width: 350; height: auto'>NEXUS Artifact:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_ARTIFACT}</td></tr><tr><td style='width: 350; height: auto'>NEXUS Version:</td><td style='width: 350; height: auto'>${wfs.env.NEXUS_VERSION}</td></tr></table></body></html>", attachmentsPattern: "**/*.html")
                }
            }
        }

        return (null)
    }
}
