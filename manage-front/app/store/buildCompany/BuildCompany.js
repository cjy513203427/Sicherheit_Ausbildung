Ext.define('Admin.store.buildCompany.BuildCompany', {
    extend: 'Admin.store.Base',
    storeId: 'buildCompany.BuildCompany',
    model: 'Admin.model.buildCompany.BuildCompany',

    requires: [
        'Admin.model.buildCompany.BuildCompany'
    ],

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('BuildCompany', 'read')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});
var companyStore = Ext.create("Ext.data.Store", {
    fields: ["status", "value"],
    data: [
        {status: "有效", value: 1},
        {status: "无效", value: 0}
    ]
});



