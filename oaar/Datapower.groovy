package ru.sbertech.oaar

public class Datapower extends Object implements Serializable {
    private def wfs, host, port, login, passwd, domain

    private def toArrayList(def list) {
        def result = new ArrayList(), i

        for (i = 0; i != list.size(); i += 1) {
            result.add(String.format(list.get(i).text().trim().toString()))
        }

        return result
    }

    private def RunCommand(def url, def request) {
        def result

        this.wfs.httpRequest(ignoreSslErrors: true, customHeaders: [[name: String.format("Authorization"), value: String.format("Basic %s", String.format("%s:%s", this.login, this.passwd).getBytes(String.format("ISO-8859-1")).encodeBase64().toString())]], responseHandle: String.format("NONE"), httpMode: String.format("POST"), url: String.format(url.toString()), requestBody: String.format(request.toString()), outputFile: String.format("%s/httpRequest.bin", this.wfs.pwd()))
        result = this.wfs.readFile(file: String.format("%s/httpRequest.bin", this.wfs.pwd()), encoding: String.format("ISO-8859-1"))

        return result
    }

    private def Process(def url, def request) {
        def result = new ArrayList(), response, xml, tmp

        this.wfs.echo(String.format("Request = %s", request.toString()))
        response = this.RunCommand(String.format(url.toString()), String.format(request.toString()))
        this.wfs.echo(String.format("Response = %s", response.toString()))
        xml = new XmlSlurper().parseText(String.format(response.toString())).declareNamespace(dp: String.format("http://www.datapower.com/schemas/management"), amp: String.format("http://www.datapower.com/schemas/appliance/management/3.0"), soap: String.format("http://schemas.xmlsoap.org/soap/envelope/"))
        tmp = this.toArrayList(xml."soap:Body"."amp:Fault".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("")) == 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."soap:Fault"."faultstring".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("")) == 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."dp:response"."dp:file".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("")) != 0)) {
                throw new Exception(String.format("Response from server is empty."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."dp:response"."dp:config"."XMLManager"."@name".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("")) != 0)) {
                throw new Exception(String.format("Response from server is empty."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."dp:response"."dp:config"."ConfigDeploymentPolicy"."@name".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("")) != 0)) {
                throw new Exception(String.format("Response from server is empty."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."amp:GetDomainListResponse"."amp:Domain".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("")) != 0)) {
                throw new Exception(String.format("Response from server is empty."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."dp:response"."dp:result".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("OK")) == 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."amp:QuiesceResponse"."amp:Status".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("error")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."amp:UnquiesceResponse"."amp:Status".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("error")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."amp:StopDomainResponse"."amp:Status".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("error")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."amp:StartDomainResponse"."amp:Status".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("error")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."amp:RestartDomainResponse"."amp:Status".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("error")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."amp:GetDomainListResponse"."amp:Status".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("error")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."dp:response"."dp:import"."import-results"."file-copy-log"."file-result"."@result".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("ERROR")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }
        tmp = this.toArrayList(xml."soap:Body"."dp:response"."dp:import"."import-results"."exec-script-results"."cfg-result"."@status".toList())
        if ((tmp.size() != 0)) {
            if ((tmp.count(String.format("ERROR")) != 0)) {
                throw new Exception(String.format("Response from server contains error."))
            }
            result = tmp
        }

        return result
    }

    public def Datapower(def wfs, def host, def port, def login, def passwd, def domain) {
        this.wfs = wfs
        this.host = host
        this.port = port
        this.login = login
        this.passwd = passwd
        this.domain = domain

        return
    }

    public def Quiesce() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Quiesce"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:QuiesceRequest><amp:Domain><amp:Name>%s</amp:Name><amp:Timeout>0</amp:Timeout></amp:Domain></amp:QuiesceRequest></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Quiesce"))

        return result
    }

    public def Unquiesce() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Unquiesce"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:UnquiesceRequest><amp:Domain><amp:Name>%s</amp:Name></amp:Domain></amp:UnquiesceRequest></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Unquiesce"))

        return result
    }

    public def Stop() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Stop"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:StopDomainRequest><amp:Domain>%s</amp:Domain></amp:StopDomainRequest></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Stop"))

        return result
    }

    public def Start() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Start"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:StartDomainRequest><amp:Domain>%s</amp:Domain></amp:StartDomainRequest></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Start"))

        return result
    }

    public def Restart() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Restart"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:RestartDomainRequest><amp:Domain>%s</amp:Domain></amp:RestartDomainRequest></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Restart"))

        return result
    }

    public def Delete() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Delete"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"default\"><dp:del-config><Domain name=\"%s\"></Domain></dp:del-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Delete"))

        return result
    }

    public def Create() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Create"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) != 0)) {
                    throw new Exception(String.format("Domain %s already exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"default\"><dp:set-config><Domain name=\"%s\"><NeighborDomain class=\"Domain\">default</NeighborDomain></Domain></dp:set-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Create"))

        return result
    }

    public def SaveCfg() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "SaveCfg"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-action><SaveConfig></SaveConfig></dp:do-action></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "SaveCfg"))

        return result
    }

    public def FlushDocument() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "FlushDocument"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:get-config class=\"XMLManager\"></dp:get-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != result.size(); i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-action><FlushDocumentCache><XMLManager>%s</XMLManager></FlushDocumentCache></dp:do-action></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), result.get(i).toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "FlushDocument"))

        return result
    }

    public def FlushStylesheet() {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "FlushStylesheet"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:get-config class=\"XMLManager\"></dp:get-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != result.size(); i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-action><FlushStylesheetCache><XMLManager>%s</XMLManager></FlushStylesheetCache></dp:do-action></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), result.get(i).toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "FlushStylesheet"))

        return result
    }

    public def LogLevel(def level) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "LogLevel"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-action><SetLogLevel><LogLevel>%s</LogLevel></SetLogLevel></dp:do-action></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), level.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "LogLevel"))

        return result
    }

    public def DirCreate(def directory) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "DirCreate"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-action><CreateDir><Dir>%s</Dir></CreateDir></dp:do-action></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), directory.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "DirCreate"))

        return result
    }

    public def DirDelete(def directory) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "DirDelete"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-action><RemoveDir><Dir>%s</Dir></RemoveDir></dp:do-action></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), directory.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "DirDelete"))

        return result
    }

    public def Export(def destination) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Export"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-export format=\"ZIP\" all-files=\"true\" persisted=\"true\"><dp:object name=\"all-objects\" class=\"all-classes\"></dp:object></dp:do-export></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        for (i = 0; i != result.size(); i += 1) {
            this.wfs.writeFile(file: String.format(destination.toString()), text: new String(result.get(i).toString().decodeBase64(), String.format("ISO-8859-1")), encoding: String.format("ISO-8859-1"))
        }
        this.wfs.echo(String.format("Stop %s.", "Export"))

        return result
    }

    public def Import(def source) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Import"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-import source-type=\"ZIP\" deployment-policy=\"%s\" dry-run=\"false\" overwrite-files=\"true\" overwrite-objects=\"true\"><dp:input-file>%s</dp:input-file><dp:object name=\"all-objects\" class=\"all-classes\"></dp:object></dp:do-import></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), "", this.wfs.readFile(file: String.format(source.toString()), encoding: String.format("ISO-8859-1")).getBytes(String.format("ISO-8859-1")).encodeBase64().toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Import"))

        return result
    }

    public def Install(def source, def policy) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "Install"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:get-config class=\"ConfigDeploymentPolicy\"></dp:get-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != result.size(); i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:del-config><ConfigDeploymentPolicy name=\"%s\"></ConfigDeploymentPolicy></dp:del-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), result.get(i).toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-import source-type=\"XML\" deployment-policy=\"%s\" dry-run=\"false\" overwrite-files=\"true\" overwrite-objects=\"true\"><dp:input-file>%s</dp:input-file><dp:object name=\"all-objects\" class=\"all-classes\"></dp:object></dp:do-import></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), "", this.wfs.readFile(file: String.format(policy.toString()), encoding: String.format("ISO-8859-1")).getBytes(String.format("ISO-8859-1")).encodeBase64().toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:get-config class=\"ConfigDeploymentPolicy\"></dp:get-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != result.size(); i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-import source-type=\"ZIP\" deployment-policy=\"%s\" dry-run=\"false\" overwrite-files=\"true\" overwrite-objects=\"true\"><dp:input-file>%s</dp:input-file><dp:object name=\"all-objects\" class=\"all-classes\"></dp:object></dp:do-import></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), result.get(i).toString(), this.wfs.readFile(file: String.format(source.toString()), encoding: String.format("ISO-8859-1")).getBytes(String.format("ISO-8859-1")).encodeBase64().toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "Install"))

        return result
    }

    public def FileRead(def source, def destination) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "FileRead"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:get-file name=\"%s\"></dp:get-file></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), source.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        for (i = 0; i != result.size(); i += 1) {
            this.wfs.writeFile(file: String.format(destination.toString()), text: new String(result.get(i).toString().decodeBase64(), String.format("ISO-8859-1")), encoding: String.format("ISO-8859-1"))
        }
        this.wfs.echo(String.format("Stop %s.", "FileRead"))

        return result
    }

    public def FileWrite(def source, def destination) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "FileWrite"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:set-file name=\"%s\">%s</dp:set-file></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), destination.toString(), this.wfs.readFile(file: String.format(source.toString()), encoding: String.format("ISO-8859-1")).getBytes(String.format("ISO-8859-1")).encodeBase64().toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "FileWrite"))

        return result
    }

    public def FileDelete(def source) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "FileDelete"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:do-action><DeleteFile><File>%s</File></DeleteFile></dp:do-action></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), source.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "FileDelete"))

        return result
    }

    public def ObjectDelete(def Class, def Name) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "ObjectDelete"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:del-config><%s name=\"%s\"></%s></dp:del-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), Class.toString(), Name.toString(), Class.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "ObjectDelete"))

        return result
    }

    public def ObjectModify(def Class, def Name, def Config) {
        def result = new ArrayList(), tmp, i

        this.wfs.echo(String.format("Start %s.", "ObjectModify"))
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/amp/3.0", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><amp:GetDomainListRequest></amp:GetDomainListRequest></soap:Body></soap:Envelope>"))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format(this.domain.toString())) == 0)) {
                    throw new Exception(String.format("Domain %s does not exists.", this.domain.toString()))
                }
            }
        }
        result = tmp
        tmp = result
        for (i = 0; i != 1; i += 1) {
            tmp = this.Process(String.format("https://%s:%s/service/mgmt/current", this.host.toString(), this.port.toString()), String.format("<?xml version=\"1.0\" encoding=\"UTF-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:amp=\"http://www.datapower.com/schemas/appliance/management/3.0\" xmlns:dp=\"http://www.datapower.com/schemas/management\"><soap:Body><dp:request domain=\"%s\"><dp:modify-config><%s name=\"%s\">%s</%s></dp:modify-config></dp:request></soap:Body></soap:Envelope>", this.domain.toString(), Class.toString(), Name.toString(), Config.toString(), Class.toString()))
            if ((tmp.size() != 0)) {
                if ((tmp.count(String.format("")) != 0)) {
                    throw new Exception(String.format("Nothing to do."))
                }
            }
        }
        result = tmp
        this.wfs.echo(String.format("Stop %s.", "ObjectModify"))

        return result
    }
}
