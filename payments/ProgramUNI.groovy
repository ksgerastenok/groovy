package ru.sberbank.payments

public static def Main(def wfs) {
    wfs.node("masterLin") {
        try {
            if ((wfs.env.DEPLOY_QUIESCE.toBoolean())) {
                UNI.Init(wfs)
                UNI.PlayNX(wfs, "hosts.ini", "download.yml")
                UNI.PlayMQ(wfs, "hosts.ini", "deploy.yml")
                UNI.PlayDP(wfs, "hosts.ini", "deploy.yml")
                UNI.PlayMQ(wfs, "hosts.ini", "modify.yml")
                UNI.PlayDP(wfs, "hosts.ini", "modify.yml")
            } else {
                UNI.Init(wfs)
                UNI.PlayNX(wfs, "hosts.ini", "download.yml")
                UNI.PlayMQ(wfs, "hosts.ini", "burn.yml")
                UNI.PlayDP(wfs, "hosts.ini", "burn.yml")
                UNI.PlayMQ(wfs, "hosts.ini", "modify.yml")
                UNI.PlayDP(wfs, "hosts.ini", "modify.yml")
            }
        } catch (ex) {
            UNI.Fail(wfs)
            throw (ex)
        } finally {
            UNI.Notify(wfs)
        }
    }

    return (null)
}

Main(this)
