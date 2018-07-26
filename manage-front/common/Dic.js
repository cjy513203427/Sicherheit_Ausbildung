Ext.define('Common.Dic', {

    xtype: 'dic',

    requires: [
        'Ext.util.LocalStorage',
        'Common.util.Util'
    ],

    statics: {
        config:{},
        getDicData: function (code) {
            return Ext.create('Ext.data.Store', {
                model: 'dictionary.Dictionary',
                autoLoad:true,
                proxy: {
                    type: 'ajax',
                    extraParams:{
                        typeCode:code
                    },
                    url: Common.Config.requestPath('Dictionary' , 'read'),
                    reader: {
                        type: 'json',
                        rootProperty: 'data.list'
                    }
                }
            });
        },
        // app初始化执行
        inIt: function () {
            Common.util.Util.inIt();
        }
    }
});