package ru.sberbank.payments

public static def Main(def wfs) {
    wfs.node("masterLin") {
        try {
            UNI.Init(wfs)
            UNI.PlayNX(wfs, "hosts.ini", "download.yml")
            UNI.PlayDP(wfs, "hosts.ini", "check.yml")
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
