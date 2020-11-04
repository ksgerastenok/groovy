package ru.sbertech.oaar

public class UNI extends Object implements Serializable {
    public static def Install(def wfs) {
        // Display
        wfs.currentBuild.result = "SUCCESS"
        wfs.currentBuild.displayName = "#${wfs.currentBuild.number} / ${wfs.env.envId} / ${wfs.env.NEXUS_URL_RAR.tokenize("/").getAt(-3)} / ${wfs.env.NEXUS_URL_RAR.tokenize("/").getAt(-2)}"

        // ClearWS
        wfs.stage("Clear workspace.") {
            wfs.dir("${wfs.pwd()}") {
                wfs.deleteDir()
            }
        }

        // Nexus
        wfs.stage("Get Distrib from NEXUS.") {
            wfs.withCredentials([wfs.usernamePassword(credentialsId: "${wfs.env.VAULT_NEXUS}", usernameVariable: "LOGIN", passwordVariable: "PASSWD")]) {
                wfs.dir("${wfs.pwd()}/nexus") {
                    wfs.fileOperations([wfs.fileDownloadOperation(url: "${wfs.env.NEXUS_URL_RAR}", userName: "${wfs.env.LOGIN}", password: "${wfs.env.PASSWD}", targetLocation: "${wfs.pwd()}/nexus", targetFileName: "${wfs.pwd()}/archive.zip")])
                    wfs.fileOperations([wfs.fileUnZipOperation(targetLocation: "${wfs.pwd()}", filePath: "${wfs.pwd()}/archive.zip")])
                }
            }
        }

        // InstallMQ
        wfs.stage("Get MQ vars from GIT.") {
            wfs.dir("${wfs.pwd()}/vars/mq") {
                wfs.git(url: "${wfs.env.GIT_MQ_VARS.tokenize(';').getAt(0)}", branch: "${wfs.env.GIT_MQ_VARS.tokenize(';').getAt(1)}", credentialsId: "${wfs.env.GIT_MQ_VARS.tokenize(';').getAt(2)}")
            }
        }
        wfs.stage("Get MQ scripts from GIT.") {
            wfs.dir("${wfs.pwd()}/scripts/mq") {
                wfs.git(url: "${wfs.env.GIT_MQ_SRC.tokenize(';').getAt(0)}", branch: "${wfs.env.GIT_MQ_SRC.tokenize(';').getAt(1)}", credentialsId: "${wfs.env.GIT_MQ_SRC.tokenize(';').getAt(2)}")
            }
        }
        wfs.stage("Deploy ${wfs.env.ENV} (run Ansible playbook for MQ installation).") {
            wfs.dir("${wfs.pwd()}") {
                wfs.ansiblePlaybook(installation: "ansible26", inventory: "${wfs.pwd()}/vars/mq/${wfs.env.GIT_MQ_VARS.tokenize(';').getAt(3)}/${wfs.env.envId}/hosts.ini", playbook: "${wfs.pwd()}/scripts/mq/${wfs.env.GIT_MQ_SRC.tokenize(';').getAt(3)}/playbook.yml", extraVars: [segment: "${wfs.env.SEGMENT}", alias: "${wfs.env.IGW}", folder: "${wfs.pwd()}/nexus"], vaultCredentialsId: "${wfs.env.VAULT_MQ}", sudoUser: null, forks: 5)
            }
        }

        // InstallDP
        wfs.stage("Get DP vars from GIT.") {
            wfs.dir("${wfs.pwd()}/vars/dp") {
                wfs.git(url: "${wfs.env.GIT_DP_VARS.tokenize(';').getAt(0)}", branch: "${wfs.env.GIT_DP_VARS.tokenize(';').getAt(1)}", credentialsId: "${wfs.env.GIT_DP_VARS.tokenize(';').getAt(2)}")
            }
        }
        wfs.stage("Get DP scripts from GIT.") {
            wfs.dir("${wfs.pwd()}/scripts/dp") {
                wfs.git(url: "${wfs.env.GIT_DP_SRC.tokenize(';').getAt(0)}", branch: "${wfs.env.GIT_DP_SRC.tokenize(';').getAt(1)}", credentialsId: "${wfs.env.GIT_DP_SRC.tokenize(';').getAt(2)}")
            }
        }
        wfs.stage("Deploy ${wfs.env.ENV} (run Ansible playbook for DP installation).") {
            wfs.dir("${wfs.pwd()}") {
                wfs.ansiblePlaybook(installation: "ansible26", inventory: "${wfs.pwd()}/vars/dp/${wfs.env.GIT_DP_VARS.tokenize(';').getAt(3)}/${wfs.env.envId}/hosts.ini", playbook: "${wfs.pwd()}/scripts/dp/${wfs.env.GIT_DP_SRC.tokenize(';').getAt(3)}/playbook.yml", extraVars: [segment: "${wfs.env.SEGMENT}", alias: "${wfs.env.IGW}", folder: "${wfs.pwd()}/nexus"], vaultCredentialsId: "${wfs.env.VAULT_DP}", sudoUser: null, forks: 5)
            }
        }

        return null
    }

    public static def Restore(def wfs) {
        // Display
        wfs.currentBuild.result = "FAILURE"
        wfs.currentBuild.displayName = "#${wfs.currentBuild.number} / ${wfs.env.envId} / ${wfs.env.NEXUS_URL_RAR.tokenize("/").getAt(-3)} / ${wfs.env.NEXUS_URL_RAR.tokenize("/").getAt(-2)}"

        return null
    }
}
