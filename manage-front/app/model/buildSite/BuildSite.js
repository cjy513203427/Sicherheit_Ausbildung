Ext.define('Admin.model.buildSite.BuildSite', {
    extend: 'Admin.model.Base',
    idProperty: 'id',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'code', type: 'string'},
        {name: 'address', type: 'string'},
        {name: 'accountNumber', type: 'int'},
        {name: 'unitPrice', type: 'int'},
        {name: 'contactName', type: 'string'},
        {name: 'contactPhone', type: 'string'},
        {name: 'createUserId', type: 'int'},
        {name: 'createUserName', type: 'string'},
        {name: 'status', type: 'string'},
        {name: 'salemanId', type: 'int'},
        {name: 'salemanName', type: 'string'},
        {name: 'createTime', type: 'string'},
        {name: 'updateTime', type: 'string'}
    ]
});