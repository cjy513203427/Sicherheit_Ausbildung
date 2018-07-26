Ext.define('Admin.store.buildLabourer.BuildLabourer', {
    extend: 'Admin.store.Base',
    storeId: 'buildLabourer.BuildLabourer',
    model: 'Admin.model.buildLabourer.BuildLabourer',

    requires: [
        'Admin.model.buildLabourer.BuildLabourer'
    ],

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('BuildLabourer', 'read')
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

var sexStore = Ext.create("Ext.data.Store", {
    fields: ["sex", "value"],
    data: [
        {sex: "男", value: 1},
        {sex: "女", value: 2},
        {sex: "其他", value: 3}
    ]
});

/*1:管理人员、2：作业人员、3：普通工人、9：其他*/
var postTypeStore = Ext.create("Ext.data.Store", {
    fields: ["postType", "value"],
    data: [
        {postType: "管理人员", value: 1},
        {postType: "作业人员", value: 2},
        {postType: "普通工人", value: 3},
        {postType: "其他", value: 9}
    ]
});



