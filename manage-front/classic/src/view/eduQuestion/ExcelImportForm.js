/**
 * @author cjy
 * @Date 2018/6/5 9:47.
 */
Ext.define('Admin.view.eduQuestion.ExcelImportForm', {
    extend: 'Ext.window.Window',
    xtype: 'excelImportForm',

    title: '导入表格',

    requires: [
        'Admin.view.eduQuestion.ExcelImportFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 200,
    width: 370,

    controller: 'excelImportForm',


    items: [{
        flex: 1,
        xtype: 'form',
        reference: 'form',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'textfield',
            hidden:true,
            name:'fileName',
            id:'fileName'
        },{
            xtype: 'textfield',
            hidden:true,
            name:'questionBankId'
        },{
            xtype: 'textfield',
            hidden:true,
            name:'courseId'
        },{
            xtype: 'filefield',
            name: 'excelPath',
            id:'excelPath',
            fieldLabel: '表格',
            labelWidth: 50,
            msgTarget: 'side',
            anchor: '100%',
            accept: 'xls/xlsx',
            buttonText: '选择Excel表格...',
            validator: function (value) {
                if(value==''){
                    return true;
                }
                var arr = value.split('.');
                if (arr[arr.length - 1] == 'xls' || arr[arr.length - 1] == 'xlsx') {
                    return true;
                } else {
                    return '必须选择xls或者xlsc格式的Excel！';
                }
            }
        }]
    }],
    buttons: [{
        text: '确定',
        handler: 'editExcel'
    }, {
        text: '取消',
        handler: 'closeWindow'
    }]
});
