/**
 * Created by jonnyLee on 2016/09/27.
 */
Ext.define('Admin.view.picArticle.PicArticlePdfForm', {
    extend: 'Ext.window.Window',
    xtype: 'picArticlePdfForm',

    // title: '菜单添加',

    requires: [
        'Admin.view.picArticle.PicArtiFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 350,
    width: 400,
    controller: 'picarticleform',

    viewModel: {
        links: {
            thePicArticle: {
                type: 'picArticle.PicArticle',
                create: true
            }
        }
    },

    items: [{
        xtype: 'form',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype: 'textfield',
            name: 'id',
            fieldLabel: 'Id',
            hidden: true,
            bind: '{thePicArticle.id}'
        }, {
            xtype: 'textfield',
            name:'contentClassify'
            ,hidden:true
        },{
            xtype: 'textfield',
            name: 'title',
            fieldLabel: '标题',
            bind: '{thePicArticle.title}'
        }, {
            xtype: 'combo',
            fieldLabel: '岗位',
            name: 'postType',
            displayField: 'text',
            valueField: 'value',
            editable: false,
            store: Common.Dic.getDicData('POST_TYPE'),
            bind: '{thePicArticle.postTypeTxt}'
        },{
            width: 300,
            xtype: 'filefield',
            name: 'file',
            msgTarget: 'side',
            allowBlank: false,
            emptyText: 'PDF上传',
            accept: 'application/pdf',
            buttonConfig: {
                xtype: 'filebutton',
                iconCls: 'x-fa fa-cloud-upload',
                text: '选择文件'
            }
        }, {
            xtype: 'textareafield',
            name: 'introduce',
            fieldLabel: '简介',
            bind: '{thePicArticle.introduce}'
        }]
    }],
    buttons: [{
        text: '确定',
        handler: 'editPicArticlePdf'
    }, {
        text: '取消',
        handler: 'closePicArticleWindow'
    }]
});
