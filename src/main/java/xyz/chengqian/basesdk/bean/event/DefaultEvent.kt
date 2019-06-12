package xyz.chengqian.basesdk.bean.event

class DefaultEvent {
    private constructor()
    constructor(arg1:Int){
        this.arg1=arg1
    }
    constructor(arg1:Int,any: Any?){
        this.arg1=arg1
        this.any=any
    }
    constructor(arg1:Int,arg2:Int,any: Any?){
        this.arg1=arg1
        this.arg2=arg2
        this.any=any
    }
    var arg1 = 0
    var arg2 = 0
    var any: Any? = null
}
