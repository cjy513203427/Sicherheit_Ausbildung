Ext.define('Admin.model.buildCompany.BuildCompany', {
    extend: 'Admin.model.Base',
    idProperty: 'id',
    fields: [
        {name: 'id', type: 'int'},
        {name: 'companyName', type: 'string'},
        {name: 'accountNumber', type: 'int'},
        {name: 'unitPrice', type: 'int'},
        {name: 'createUserId', type: 'int'},
        {name: 'createUsername', type: 'string'},
        {name: 'status', type: 'string'},
        {name: 'salemanId', type: 'int'},
        {name: 'salesmanName', type: 'string'},
        {name: 'createTime', type: 'string'},
        {name: 'updateTime', type: 'string'}
    ]
});