package ru.sbertech.oaar

try {
    IGW.RunMQ(this)
    IGW.RunDP(this)
}
catch(Exception ex) {
    this.echo(ex.message)
    throw new Exception(ex.message)
}
finally {
    IGW.RunKIT(this)
}
