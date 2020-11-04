package ru.sberbank.payments

public static def Main(def wfs) {
    wfs.node("masterLin") {
        try {
            UNI.Init(wfs)
            UNI.PlayDP(wfs, "hosts.ini", "delete.yml")
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
