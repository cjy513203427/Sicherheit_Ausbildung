Ext.define('Admin.view.buildLabourer.BuildLabourerForm', {
    extend: 'Ext.window.Window',
    xtype: 'BuildLabourerForm',

    title: '新增建筑工地人员(默认密码：123456)',

    requires: [
        'Admin.view.buildLabourer.BuildLabourerForm',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',
    closeToolText: '关闭窗口',
    modal: true,
    height: 720,
    width: 350,

    controller: 'buildLabourerForm',

    viewModel: {
        links: {
            theBuildLabourer: {
                type: 'buildLabourer.BuildLabourer',
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
            id: 'hiddenLabourerId',
            name: 'id',
            bind: {
                value: '{theBuildLabourer.id}'
            }
        }, {
            xtype: 'textfield',
            name: 'buildSiteCode',
            allowBlank: false,
            disabled: true,
            fieldLabel: '工地编号',
            bind: {
                value: '{theBuildLabourer.buildSiteCode}'
            }
        }, {
            xtype: 'textfield',
            name: 'realname',
            allowBlank: false,
            fieldLabel: '姓名',
            bind: {
                value: '{theBuildLabourer.realname}'
            }
        }, {
            xtype: 'textfield',
            name: 'idCard',
            allowBlank: false,
            fieldLabel: '身份证号码',
            regex: /^((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\d{3}(x|X))|(\d{4}))$/,
            regexText: '不是正确的身份证号',
            bind: {
                value: '{theBuildLabourer.idCard}'
            }
        }, {
            xtype: 'combobox',
            name: 'sex',
            fieldLabel: '性别',
            store: sexStore,
            editable: false,
            displayField: 'sex',
            valueField: 'value',
            emptyText: '--请选择--',
            queryMode: 'local',
            bind: {
                value: '{theBuildLabourer.sex}'
            }
        }, {
            xtype: 'textfield',
            name: 'address',
            allowBlank: false,
            fieldLabel: '地址',
            bind: {
                value: '{theBuildLabourer.address}'
            }
        }, {
            xtype: 'textfield',
            name: 'phone',
            allowBlank: false,
            fieldLabel: '手机号',
            regex: /^1[3|4|5|6|7|8][0-9]{9}$/,
            regexText: '不是正确的手机号',
            bind: {
                value: '{theBuildLabourer.phone}'
            }
        }, {
            xtype: 'combobox',
            name: 'postType',
            fieldLabel: '岗位',
            store: postTypeStore,
            editable: false,
            displayField: 'postType',
            valueField: 'value',
            emptyText: '--请选择--',
            queryMode: 'local',
            bind: {
                value: '{theBuildLabourer.postType}'
            }
        }, {
            xtype: 'textfield',
            name: 'leaderName',
            fieldLabel: '上级领导姓名',
            allowBlank: false,
            bind: {
                value: '{theBuildLabourer.leaderName}'
            }
        }, {
            xtype: 'textfield',
            name: 'leaderPhone',
            fieldLabel: '上级领导手机',
            allowBlank: false,
            regex: /^1[3|4|5|6|7|8][0-9]{9}$/,
            regexText: '不是正确的手机号',
            bind: {
                value: '{theBuildLabourer.leaderPhone}'
            }
        }, {
            xtype: 'textfield',
            name: 'nation',
            fieldLabel: '民族',
            allowBlank: false,
            bind: {
                value: '{theBuildLabourer.nation}'
            }
        }, {
            xtype: 'textfield',
            name: 'birthday',
            fieldLabel: '生日',
            allowBlank: false,
            bind: {
                value: '{theBuildLabourer.birthday}'
            }
        }, {
            xtype: 'textfield',
            name: 'idIssued',
            fieldLabel: '签发机关',
            allowBlank: false,
            bind: {
                value: '{theBuildLabourer.idIssued}'
            }
        }, {
            xtype: 'textfield',
            name: 'issuedDate',
            fieldLabel: '有效开始日期',
            allowBlank: false,
            bind: {
                value: '{theBuildLabourer.issuedDate}'
            }
        }, {
            xtype: 'textfield',
            name: 'validDate',
            fieldLabel: '有效截止日期',
            allowBlank: false,
            bind: {
                value: '{theBuildLabourer.validDate}'
            }
        }, {
            xtype: 'textfield',
            name: 'base64Photo',
            fieldLabel: 'base64图片文件',
            hidden: true,
            allowBlank: false,
            bind: {
                value: '{theBuildLabourer.base64Photo}'
            }
        }],
        buttons: [{
            text: '获取身份证信息',
            handler: 'getIdentityCardInfo'
        }, {
            text: '确定',
            handler: 'editorBuildLabourer'
        }, {
            text: '取消',
            handler: 'closeLabourerWindow'
        }]
    }
});
