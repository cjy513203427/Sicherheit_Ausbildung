Ext.define('Admin.view.buildCompany.BuildCompanyForm', {
    extend: 'Ext.window.Window',
    xtype: 'BuildCompanyForm',

    title: '新增建筑公司',

    requires: [
        'Admin.view.buildCompany.BuildCompanyForm',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',
    closeToolText: '关闭窗口',
    modal: true,
    height: 400,
    width: 350,

    controller: 'buildCompanyForm',

    viewModel: {
        links: {
            theBuildCompany: {
                type: 'buildCompany.BuildCompany',
                create: true
            }
        },
        data: {
            roleComboQueryMode: 'remote'
        }
    },

    items: {
        xtype: 'form',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            labelWidth: 80,
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'hidden',
            id: 'hiddenCompanyId',
            name: 'id',
            bind: {
                value: '{theBuildCompany.id}'
            }
        }, {
            xtype: 'textfield',
            name: 'companyName',
            allowBlank: false,
            fieldLabel: '公司名称',
            bind: {
                value: '{theBuildCompany.companyName}'
            }
        }, {
            xtype: 'numberfield',
            name: 'accountNumber',
            fieldLabel: '账户数量',
            allowBlank: false,
            bind: {
                value: '{theBuildCompany.accountNumber}'
            }
        }, {
            xtype: 'numberfield',
            name: 'unitPrice',
            fieldLabel: '账户单价',
            allowBlank: false,
            bind: {
                value: '{theBuildCompany.unitPrice}'
            }
        }, {
            xtype: 'combo',
            store: 'users.User',
            multiSelect: false,
            displayField: 'realname',
            valueField: 'id',
            name: 'salesmanId',
            fieldLabel: '销售人员',
            emptyText: '请选择销售人员',
            allowBlank: false,
            bind: {
                value: '{theBuildCompany.salesmanId}'
            }
        }],
        buttons: [{
            text: '确定',
            handler: 'editorBuildCompany'
        }, {
            text: '取消',
            handler: 'closeCompanyWindow'
        }]
    }
});
