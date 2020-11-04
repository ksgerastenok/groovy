package ru.sbertech.oaar

public static def Main(def wfs) {
    wfs.node("OPIR-Slave3-linux") {
        try {
            UNI.Install(wfs)
            wfs.echo("OK")
            if((wfs.env.getEnvironment().containsKey("JOB_TESTS"))) {
                wfs.stage("Autotests.") {
                    wfs.dir("${wfs.pwd()}") {
                        wfs.build(job: "${wfs.env.JOB_TESTS}", parameters: [wfs.string(name: "NEXUS_URL", value: "${wfs.env.NEXUS_URL_RAR}")], propagate: false, wait: false)
                    }
                }
            }
        } catch (ex) {
            UNI.Restore(wfs)
            wfs.echo(ex.message)
        } finally {
            if((wfs.env.getEnvironment().containsKey("NEXUS_URL_RAR"))) {
                wfs.stage("Close ticket.") {
                    wfs.dir("${wfs.pwd()}") {
                        wfs.build(job: "OPIR/SRV/SPCloseTicket", parameters: [wfs.string(name: "environment", value: "${wfs.params.envId}"), wfs.string(name: "groupid", value: "${wfs.params.groupid}"), wfs.string(name: "artifactid", value: "${wfs.params.artifactid}"), wfs.string(name: "version", value: "${wfs.params.version}"), wfs.string(name: "jiraid", value: "${wfs.params.jiraid}"), wfs.string(name: "ticketid", value: "${wfs.params.ticketid}"), wfs.string(name: "nexusUrl", value: "${wfs.params.NEXUS_URL_RAR}"), wfs.string(name: "build", value: "${wfs.currentBuild.number}"), wfs.string(name: "result", value: "${wfs.currentBuild.result}")])
                    }
                }
            }
        }
    }

    return null
}

Main(this)
