Ext.define('Admin.view.buildSite.BuildSiteForm', {
    extend: 'Ext.window.Window',
    xtype: 'BuildSiteForm',

    title: '新增建筑工地',

    requires: [
        'Admin.view.buildSite.BuildSiteForm',
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

    controller: 'buildSiteForm',

    viewModel: {
        links: {
            theBuildSite: {
                type: 'buildSite.BuildSite',
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
            id: 'hiddenSiteId',
            name: 'id',
            bind: {
                value: '{theBuildSite.id}'
            }
        }, {
            xtype: 'textfield',
            name: 'contactName',
            allowBlank: false,
            fieldLabel: '联系人名称',
            bind: {
                value: '{theBuildSite.contactName}'
            }
        }, {
            xtype: 'textfield',
            name: 'contactPhone',
            allowBlank: false,
            fieldLabel: '联系电话',
            regex: /^1[3|4|5|6|7|8][0-9]{9}$/,
            regexText: '不是正确的手机号',
            bind: {
                value: '{theBuildSite.contactPhone}'
            }
        }, {
            xtype: 'textfield',
            name: 'address',
            fieldLabel: '工地地址',
            allowBlank: false,
            bind: {
                value: '{theBuildSite.address}'
            }
        }, , {
            xtype: 'combo',
            store: 'users.User',
            multiSelect: false,
            displayField: 'realname',
            valueField: 'id',
            name: 'salemanId',
            fieldLabel: '销售人员',
            emptyText: '请选择销售人员',
            allowBlank: false,
            bind: {
                value: '{theBuildSite.salemanId}'
            }
        }, {
            xtype: 'numberfield',
            name: 'unitPrice',
            fieldLabel: '账户单价',
            allowBlank: false,
            bind: {
                value: '{theBuildSite.unitPrice}'
            }
        }, {
            xtype: 'numberfield',
            name: 'accountNumber',
            fieldLabel: '账户数量',
            allowBlank: false,
            bind: {
                value: '{theBuildSite.accountNumber}'
            }
        }],
        buttons: [{
            text: '确定',
            handler: 'editorBuildSite'
        }, {
            text: '取消',
            handler: 'closeSiteWindow'
        }]
    }
});
