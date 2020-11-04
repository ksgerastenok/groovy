package ru.sberbank.payments

public static def Main(def wfs) {
    wfs.node("masterLin") {
        try {
            UNI.Init(wfs)
            UNI.PlayMQ(wfs, "hosts.ini", "modify.yml")
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
