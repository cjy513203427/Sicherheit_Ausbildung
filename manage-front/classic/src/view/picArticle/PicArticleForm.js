/**
 * Created by jonnyLee on 2016/09/27.
 */
Ext.define('Admin.view.picArticle.PicArticleForm', {
    extend: 'Ext.window.Window',
    xtype: 'picArticleForm',

    // title: '菜单添加',

    requires: [
        'Admin.view.picArticle.PicArtiFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.HBox',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 520,
    width: 900,
    controller: 'picarticleform',

    viewModel: {
        links: {
            thePicArticle: {
                type: 'picArticle.PicArticle',
                create: true
            }
        }
    },

    items:[{
        xtype:'form',
        layout:'hbox',
        items: [{
            flex:1,
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
            }, {
                xtype: 'textareafield',
                name: 'introduce',
                fieldLabel: '简介',
                bind: '{thePicArticle.introduce}'
            }]
        },{
            flex:2,
            xtype: 'ueditor',
            name :'content',
            height: 400
        }],
        buttons: [{
            text: '确定',
            handler: 'editPicArticle'
        }, {
            text: '取消',
            handler: 'closePicArticleWindow'
        }]
    }]
});
