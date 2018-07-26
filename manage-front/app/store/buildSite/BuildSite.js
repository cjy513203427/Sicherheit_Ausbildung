Ext.define('Admin.store.buildSite.BuildSite', {
    extend: 'Admin.store.Base',
    storeId: 'buildSite.BuildSite',
    model: 'Admin.model.buildSite.BuildSite',

    requires: [
        'Admin.model.buildSite.BuildSite'
    ],

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('BuildSite', 'read')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});
var genderStore = Ext.create("Ext.data.Store", {
    fields: ["status", "value"],
    data: [
        {status: "启用", value: 1},
        {status: "禁用", value: 0}
    ]
});
