package ru.sbertech.oaar

public class IGW extends Object implements Serializable {

    private static def GenReport(def message) {
        def result, list, xml, i

        xml = new XmlSlurper().parseText(String.format(message.toString())).declareNamespace(dp: String.format("http://www.datapower.com/schemas/management"), amp: String.format("http://www.datapower.com/schemas/appliance/management/3.0"), soap: String.format("http://schemas.xmlsoap.org/soap/envelope/"))
        result = new String()
        result = String.format("%s%s", result, "<html xmlns=\"http://www.w3.org/TR/REC-html40\">")
        result = String.format("%s%s", result, "<head>")
        result = String.format("%s%s", result, "<title>")
        result = String.format("%s%s", result, "font-family")
        result = String.format("%s%s", result, "</title>")
        result = String.format("%s%s", result, "<style>")
        result = String.format("%s%s", result, "p { font-weight: normal; font-family: Geneva, Calibri, Helvetica, sans-serif; }")
        result = String.format("%s%s", result, "h1 { font-weight: normal; font-family: Geneva, Calibri, Helvetica, sans-serif; }")
        result = String.format("%s%s", result, "tr { font-weight: normal; font-family: Geneva, Calibri, Helvetica, sans-serif; }")
        result = String.format("%s%s", result, "td { font-weight: normal; font-family: Geneva, Calibri, Helvetica, sans-serif; }")
        result = String.format("%s%s", result, "</style>")
        result = String.format("%s%s", result, "</head>")
        result = String.format("%s%s", result, "<body>")
        result = String.format("%s%s", result, "<table width=\"100%\" align=\"justyfy\" valign=\"top\" border=\"1\" bordercolor=\"gray\">")
        result = String.format("%s%s", result, "<tr>")
        result = String.format("%s%s", result, "<td colspan=\"3\" bgcolor=\"#fff5d7\">")
        result = String.format("%s%s", result, "<h1>")
        result = String.format("%s%s", result, "Результаты Автотестов")
        result = String.format("%s%s", result, "</h1>")
        result = String.format("%s%s", result, "</td>")
        result = String.format("%s%s", result, "</tr>")
        result = String.format("%s%s", result, "<tr>")
        result = String.format("%s%s", result, "<td colspan=\"2\" bgcolor=\"#C0C0C0\">")
        result = String.format("%s%s", result, "Результат")
        result = String.format("%s%s", result, "</td>")
        if((!(((xml."testsuite"."@failures".toString() == String.format("")) || (xml."testsuite"."@failures".toString() == String.format("0"))) && ((xml."testsuite"."@errors".toString() == String.format("")) || (xml."testsuite"."@errors".toString() == String.format("0")))))) {
            result = String.format("%s%s", result, "<td colspan=\"1\" bgcolor=\"#FA5858\">")
            result = String.format("%s%s", result, "FAIL")
            result = String.format("%s%s", result, "</td>")
        } else {
            result = String.format("%s%s", result, "<td colspan=\"1\" bgcolor=\"#BEF781\">")
            result = String.format("%s%s", result, "SUCCESS")
            result = String.format("%s%s", result, "</td>")
        }
        result = String.format("%s%s", result, "</tr>")
        result = String.format("%s%s", result, "<tr>")
        result = String.format("%s%s", result, "<td colspan=\"1\" bgcolor=\"#fff5d7\" width=\"15%\">")
        result = String.format("%s%s", result, "<b>")
        result = String.format("%s%s", result, "Тестовый комплект")
        result = String.format("%s%s", result, "</b>")
        result = String.format("%s%s", result, "</td>")
        result = String.format("%s%s", result, "<td colspan=\"1\" bgcolor=\"#fff5d7\" width=\"15%\">")
        result = String.format("%s%s", result, "<b>")
        result = String.format("%s%s", result, "Статус")
        result = String.format("%s%s", result, "</b>")
        result = String.format("%s%s", result, "</td>")
        result = String.format("%s%s", result, "<td colspan=\"1\" bgcolor=\"#fff5d7\" width=\"70%\">")
        result = String.format("%s%s", result, "<b>")
        result = String.format("%s%s", result, "Результат")
        result = String.format("%s%s", result, "</b>")
        result = String.format("%s%s", result, "</td>")
        result = String.format("%s%s", result, "</tr>")
        list = xml."testsuite"."testcase".toList()
        for (i = 0; i != list.size(); i += 1) {
            result = String.format("%s%s", result, "<tr>")
            result = String.format("%s%s", result, "<td colspan=\"1\" width=\"15%\">")
            result = String.format("%s%s", result, "<p>")
            result = String.format("%s%s", result, list.get(i)."@name")
            result = String.format("%s%s", result, "</p>")
            result = String.format("%s%s", result, "</td>")
            result = String.format("%s%s", result, "<td colspan=\"1\" width=\"15%\">")
            result = String.format("%s%s", result, "<p>")
            result = String.format("%s%s", result, list.get(i)."@status")
            result = String.format("%s%s", result, "</p>")
            result = String.format("%s%s", result, "</td>")
            result = String.format("%s%s", result, "<td colspan=\"1\" width=\"70%\">")
            result = String.format("%s%s", result, "<p>")
            result = String.format("%s%s", result, list.get(i)."system-out")
            result = String.format("%s%s", result, "</p>")
            result = String.format("%s%s", result, "</td>")
            result = String.format("%s%s", result, "</tr>")
        }
        result = String.format("%s%s", result, "</table>")
        result = String.format("%s%s", result, "</body>")
        result = String.format("%s%s", result, "</html>")

        return result
    }

    private static def ParsePOM(def url) {
        def result = new ArrayList(), list, xml, i

        xml = new XmlSlurper().parse(url.toString().replaceAll(String.format("\\.zip"), String.format("\\.pom")))
        list = xml."groupId".toList()
        if((list.size() == 1)) {
            for (i = 0; i != list.size(); i += 1) {
                result.add(list.get(i).toString())
            }
        }
        list = xml."artifactId".toList()
        if((list.size() == 1)) {
            for (i = 0; i != list.size(); i += 1) {
                result.add(list.get(i).toString())
            }
        }
        list = xml."version".toList()
        if((list.size() == 1)) {
            for (i = 0; i != list.size(); i += 1) {
                result.add(list.get(i).toString())
            }
        }

        return result
    }

    private static def CloseTicket(def wfs) {
        if((wfs.currentBuild.result == null)) {
            wfs.build(job: String.format("OPIR/SRV/SPCloseTicket"), parameters: [wfs.string(name: String.format("environment"), value: wfs.envId.toString()), wfs.string(name: String.format("groupid"), value: wfs.groupid.toString()), wfs.string(name: String.format("artifactid"), value: wfs.artifactid.toString()), wfs.string(name: String.format("version"), value: wfs.version.toString()), wfs.string(name: String.format("jiraid"), value: wfs.jiraid.toString()), wfs.string(name: String.format("ticketid"), value: wfs.ticketid.toString()), wfs.string(name: String.format("nexusUrl"), value: wfs.NEXUS_URL_RAR.toString()), wfs.string(name: String.format("build"), value: wfs.currentBuild.number.toString()), wfs.string(name: String.format("result"), value: 'SUCCESS')])
        } else {
            wfs.build(job: String.format("OPIR/SRV/SPCloseTicket"), parameters: [wfs.string(name: String.format("environment"), value: wfs.envId.toString()), wfs.string(name: String.format("groupid"), value: wfs.groupid.toString()), wfs.string(name: String.format("artifactid"), value: wfs.artifactid.toString()), wfs.string(name: String.format("version"), value: wfs.version.toString()), wfs.string(name: String.format("jiraid"), value: wfs.jiraid.toString()), wfs.string(name: String.format("ticketid"), value: wfs.ticketid.toString()), wfs.string(name: String.format("nexusUrl"), value: wfs.NEXUS_URL_RAR.toString()), wfs.string(name: String.format("build"), value: wfs.currentBuild.number.toString()), wfs.string(name: String.format("result"), value: wfs.currentBuild.result.toString())])
        }

        return null
    }

    private static def GetDistrib(def wfs) {
        def list

        try {
            if ((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
                list = wfs.NEXUS.toString().split(":").toList()
                wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
            } else {
                list = wfs.NEXUS_URL_RAR.toString().split("/")
                wfs.ARTIFACT = list[10]
                wfs.VERSION = list[11]
            }
            wfs.cleanWs()
            wfs.httpRequest(ignoreSslErrors: true, authentication: String.format("nexus_user_cred"), responseHandle: String.format("NONE"), httpMode: String.format("GET"), url: wfs.NEXUS_URL_RAR.toString(), outputFile: String.format("%s/archive.zip", wfs.pwd().toString()))
            wfs.unzip(dir: wfs.pwd().toString(), zipFile: String.format("%s/archive.zip", wfs.pwd().toString()), charset: String.format("ISO-8859-1"), glob: String.format("mq/**"))
            wfs.unzip(dir: wfs.pwd().toString(), zipFile: String.format("%s/archive.zip", wfs.pwd().toString()), charset: String.format("ISO-8859-1"), glob: String.format("dp/**"))
            wfs.currentBuild.result = String.format("SUCCESS")
        } catch(Exception ex) {
            wfs.currentBuild.result = String.format("FAILURE")
            wfs.echo(String.format("Failed to process this task, because of reason %s", ex.getMessage()))
            throw new Exception(ex.message)
        }

        return null
    }

    private static def InstallDP(def wfs) {
        def params, list, spec, dp, i

        if((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
            list = wfs.NEXUS.toString().split(":").toList()
            wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        } else {
            list = wfs.NEXUS_URL_RAR.toString().split("/")
            wfs.ARTIFACT = list[10]
            wfs.VERSION = list[11]
        }
        wfs.withCredentials([[$class: String.format("UsernamePasswordMultiBinding"), credentialsId: String.format("80b2eb40-eb92-471c-bcdd-4da47656898b"), usernameVariable: String.format("LOGIN"), passwordVariable: String.format("PASSWORD")]]) {
            list = wfs.DP.toString().split(String.format(";")).toList()
            spec = wfs.VERSION.toString().split(String.format("-")).toList()
            for (i = 0; i != list.size(); i += 1) {
                try {
                    params = list.get(i).toString().split(String.format(":")).toList()
                    if ((wfs.fileExists(String.format("%s/dp/%s", wfs.pwd().toString(), params.get(0).toString())))) {
                        dp = new Datapower(wfs, params.get(1).toString(), params.get(2).toString(), wfs.env.LOGIN.toString(), wfs.env.PASSWORD.toString(), params.get(3).toString())
                        dp.Quiesce()
                        //dp.Export(String.format("%s/before.zip", wfs.pwd().toString()))
                        dp.FlushDocument()
                        dp.SaveCfg()
                        try {
                            dp.Install(String.format("%s/dp/%s/domain/%s.zip", wfs.pwd().toString(), params.get(0).toString(), params.get(0).toString(), wfs.ENV.toString()), String.format("%s/dp/%s/deployment/%s.%s.xcfg", wfs.pwd().toString(), params.get(0).toString(), params.get(0).toString(), wfs.ENV.toString()))
                        }
                        catch (Exception ex) {
                            wfs.currentBuild.result = String.format("FAILURE")
                            wfs.echo(String.format("Failed to process this task, because of reason %s", ex.getMessage()))
                        }
                        dp.SaveCfg()
                        dp.FlushStylesheet()
                        //dp.Export(String.format("%s/after.zip", wfs.pwd().toString()))
                        dp.Unquiesce()
                    } else {
                        switch (spec.get(0).toString()) {
                            case "D":
                                throw new Exception(String.format("DP Domain %s is absent.", params.get(0).toString()))
                                break
                            case "P":
                                wfs.echo(String.format("DP Domain %s is absent.", params.get(0).toString()))
                                break
                            case "SP":
                                wfs.echo(String.format("DP Domain %s is absent.", params.get(0).toString()))
                                break
                            default:
                                throw new Exception(String.format("DISTRIB type %s is unknown.", spec.get(0).toString()))
                                break
                        }
                    }
                    wfs.currentBuild.result = String.format("SUCCESS")
                }
                catch (Exception ex) {
                    wfs.currentBuild.result = String.format("FAILURE")
                    wfs.echo(String.format("Failed to process this task, because of reason %s", ex.getMessage()))
                    throw new Exception(ex.message)
                }
            }
        }

        return null
    }

    private static def InstallMQ(def wfs) {
        def params, list, spec, i

        if((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
            list = wfs.NEXUS.toString().split(":").toList()
            wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        } else {
            list = wfs.NEXUS_URL_RAR.toString().split("/")
            wfs.ARTIFACT = list[10]
            wfs.VERSION = list[11]
        }
        wfs.withCredentials([[$class: String.format("UsernamePasswordMultiBinding"), credentialsId: String.format("80b2eb40-eb92-471c-bcdd-4da47656898b"), usernameVariable: String.format("LOGIN"), passwordVariable: String.format("PASSWORD")]]) {
            list = wfs.MQ.toString().split(String.format(";")).toList()
            spec = wfs.VERSION.toString().split(String.format("-")).toList()
            for (i = 0; i != list.size(); i += 1) {
                try {
                    params = list.get(i).toString().split(String.format(":")).toList()
                    if ((wfs.fileExists(String.format("%s/mq/%s", wfs.pwd().toString(), params.get(0).toString())))) {
                        if ((wfs.isUnix())) {
                            wfs.sh(String.format("cat %s/mq/%s/%s/update.mqsc | runmqsc -w 60 -m IRUS.DEPLOY.GW1 %s", wfs.pwd().toString(), params.get(0).toString(), wfs.ENV.toString(), params.get(1).toString()))
                        } else {
                            wfs.bat(String.format("type %s\\mq\\%s\\%s\\update.mqsc | runmqsc -w 60 -m IRUS.DEPLOY.GW1 %s", wfs.pwd().toString(), params.get(0).toString(), wfs.ENV.toString(), params.get(1).toString()))
                        }
                    }
                    else {
                        switch (spec.get(0).toString()) {
                            case "D":
                                throw new Exception(String.format("MQ Manager %s is absent.", params.get(0).toString()))
                                break
                            case "P":
                                wfs.echo(String.format("MQ Manager %s is absent.", params.get(0).toString()))
                                break
                            case "SP":
                                wfs.echo(String.format("MQ Manager %s is absent.", params.get(0).toString()))
                                break
                            default:
                                throw new Exception(String.format("DISTRIB type %s is unknown.", spec.get(0).toString()))
                                break
                        }
                    }
                    wfs.currentBuild.result = String.format("SUCCESS")
                }
                catch (Exception ex) {
                    wfs.currentBuild.result = String.format("FAILURE")
                    wfs.echo(String.format("Failed to process this task, because of reason %s", ex.getMessage()))
                    throw new Exception(ex.message)
                }
            }
        }

        return null
    }

    public static def ExecAutotest(def wfs) {
        def list, i

        if((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
            list = wfs.NEXUS.toString().split(":").toList()
            wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        } else {
            list = wfs.NEXUS_URL_RAR.toString().split("/")
            wfs.ARTIFACT = list[10]
            wfs.VERSION = list[11]
        }
        wfs.currentBuild.displayName = String.format("#%s / %s / %s / %s", wfs.currentBuild.number.toString(), wfs.ENV.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        wfs.withCredentials([[$class: String.format("UsernamePasswordMultiBinding"), credentialsId: String.format("f7e64a30-ac16-4a76-9380-3722b93e91b6"), usernameVariable: String.format("LOGIN"), passwordVariable: String.format("PASSWORD")]]) {
            wfs.deleteDir()
            wfs.runFromAlmBuilder(almServerName: String.format("sbt-hpalm"), almUserName: String.format(wfs.env.LOGIN.toString()), almPassword: String.format(wfs.env.PASSWORD.toString()), almDomain: String.format("ESBGW"), almProject: String.format("GW_DB_DZO"), almTestSets: String.format("Root\\ПИР28\\ИШ ДБ\\СТ\\434037 Рейтинг ФЛ для Cetelem (шлюз)"), almRunHost: String.format("127.0.0.1"), almRunMode: String.format("RUN_LOCAL"))
        }
        list = wfs.findFiles(glob: String.format("Results?????????????????.xml")).toList()
        for (i = 0; i != list.size(); i += 1) {
            wfs.mail(to: String.format("sbt-gerastenok-ks@mail.ca.sbrf.ru"), subject: String.format("Autotest"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: IGW.GenReport(wfs.readFile(file: String.format(list.get(i).path.toString()), encoding: String.format("UTF-8"))))
        }

        return null
    }

    public static def RunDP(def wfs) {
        def list, i

        if((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
            list = wfs.NEXUS.toString().split(":").toList()
            wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        } else {
            list = wfs.NEXUS_URL_RAR.toString().split("/")
            wfs.ARTIFACT = list[10]
            wfs.VERSION = list[11]
        }
        wfs.currentBuild.displayName = String.format("#%s / %s / %s / %s", wfs.currentBuild.number.toString(), wfs.ENV.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        wfs.node(String.format("DP && %s && IGW", wfs.ENV.toString())) {
            wfs.stage(String.format("Get Distrib DP")) {
                wfs.dir(wfs.pwd().toString()) {
                    IGW.GetDistrib(wfs)
                }
            }
            wfs.stage(String.format("Install DP")) {
                wfs.dir(wfs.pwd().toString()) {
                    IGW.InstallDP(wfs)
                }
            }
        }

        return null
    }

    public static def RunMQ(def wfs) {
        def list, i

        if ((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
            list = wfs.NEXUS.toString().split(":").toList()
            wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        } else {
            list = wfs.NEXUS_URL_RAR.toString().split("/")
            wfs.ARTIFACT = list[10]
            wfs.VERSION = list[11]
        }
        wfs.currentBuild.displayName = String.format("#%s / %s / %s / %s", wfs.currentBuild.number.toString(), wfs.ENV.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        wfs.node(String.format("MQ && %s && IGW", wfs.ENV.toString())) {
            wfs.stage(String.format("Get Distrib MQ")) {
                wfs.dir(wfs.pwd().toString()) {
                    IGW.GetDistrib(wfs)
                }
            }
            wfs.stage(String.format("Install MQ")) {
                wfs.dir(wfs.pwd().toString()) {
                    IGW.InstallMQ(wfs)
                }
            }
        }

        return null
    }

    public static def RunKIT(def wfs) {
        def list, i

        if((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
            list = wfs.NEXUS.toString().split(":").toList()
            wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        } else {
            list = wfs.NEXUS_URL_RAR.toString().split("/")
            wfs.ARTIFACT = list[10]
            wfs.VERSION = list[11]
        }
        wfs.currentBuild.displayName = String.format("#%s / %s / %s / %s", wfs.currentBuild.number.toString(), wfs.ENV.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        wfs.stage(String.format("Close ticket")) {
            wfs.node(String.format("masterLin", wfs.ENV.toString())) {
                wfs.dir(wfs.pwd().toString()) {
                    if((!(wfs.NEXUS_URL_RAR.toString() == String.format("")))) {
                        IGW.CloseTicket(wfs)
                    } else {
                        wfs.echo(String.format("No need to close ticket."))
                    }
                }
            }
        }

        return null
    }

    public static def RunTest(def wfs) {
        def list, i

        if((wfs.NEXUS_URL_RAR.toString() == String.format(""))) {
            list = wfs.NEXUS.toString().split(":").toList()
            wfs.NEXUS_URL_RAR = String.format("http://%s:%s/nexus/content/repositories/%s/%s/%s/%s/%s-%s-distrib.zip", list.get(0).toString(), list.get(1).toString(), list.get(2).toString(), list.get(3).toString().replaceAll("\\.", "\\/"), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        } else {
            list = wfs.NEXUS_URL_RAR.toString().split("/")
            wfs.ARTIFACT = list[10]
            wfs.VERSION = list[11]
        }
        wfs.currentBuild.displayName = String.format("#%s / %s / %s / %s", wfs.currentBuild.number.toString(), wfs.ENV.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString())
        wfs.stage(String.format("Run Autotests")) {
            wfs.node(String.format("masterWin", wfs.ENV.toString())) {
                wfs.dir(wfs.pwd().toString()) {
                    IGW.ExecAutotest(wfs)
                }
            }
        }

        return null
    }

    public static def RunSend(def wfs) {
        wfs.stage(String.format("Send mail")) {
            wfs.node(String.format("masterLin")) {
                if((wfs.currentBuild.result == null)) {
                    wfs.mail(to: String.format("sbt-gerastenok-ks@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: Успешно", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString()))
                    wfs.mail(to: String.format("kamensky-va@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: Успешно", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString()))
                    wfs.mail(to: String.format("losev-kv@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: Успешно", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString()))
                    wfs.mail(to: String.format("martynov-am@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: Успешно", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString()))
                    wfs.mail(to: String.format("piskov.e.a@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: Успешно", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString()))
                    wfs.mail(to: String.format("sbt-nochvay-sa@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: Успешно", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString()))
                    wfs.mail(to: String.format("sbt-menshikova-ai@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: Успешно", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString()))
                } else {
                    wfs.mail(to: String.format("sbt-gerastenok-ks@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: %s", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.currentBuild.result.toString()))
                    wfs.mail(to: String.format("kamensky-va@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: %s", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.currentBuild.result.toString()))
                    wfs.mail(to: String.format("losev-kv@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: %s", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.currentBuild.result.toString()))
                    wfs.mail(to: String.format("martynov-am@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: %s", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.currentBuild.result.toString()))
                    wfs.mail(to: String.format("piskov.e.a@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: %s", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.currentBuild.result.toString()))
                    wfs.mail(to: String.format("sbt-nochvay-sa@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: %s", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.currentBuild.result.toString()))
                    wfs.mail(to: String.format("sbt-menshikova-ai@mail.ca.sbrf.ru"), subject: String.format("Pipeline"), charset: String.format("windows-1251"), mimeType: String.format("text/html"), body: String.format("Завершена установка дистрибутива на ИФТ:\nhttp://sbtnexus.ca.sbrf.ru:8081/nexus/content/repositories/CC_CD_KK_repo/%s/%s/%s/%s-%s.zip\nСтатус: %s", wfs.NEXUS.toString().replaceAll(String.format("\\."), String.format("\\/")), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.ARTIFACT.toString(), wfs.VERSION.toString(), wfs.currentBuild.result.toString()))
                }
            }
        }

        return null
    }
}
