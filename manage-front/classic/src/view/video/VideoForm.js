/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.video.VideoForm', {
    extend: 'Ext.window.Window',
    xtype: 'videoForm',

    title: '视频信息添加',
    requires: [
        'Admin.view.video.VideoFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 400,
    width: 400,

    controller: 'videoform',

    viewModel: {
        links: {
            theVideo: {
                type: 'video.Video',
                create: true
            }
        },
        data: {
            roleComboQueryMode: 'remote'
        }
    },

    items: {
        xtype: 'form',
        id:'videoF',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype:'hidden',
            name:'id',
            bind:{
                value:'{theVideo.id}'
            }
        },{
            xtype: 'textfield',
            name: 'title',
            allowBlank:false,
            fieldLabel: '视频名称',
            bind: {
                value: '{theVideo.title}'
            }
        }, {
            xtype: 'textfield',
            name: 'forPeople',
            fieldLabel: '适用人群',
            allowBlank:false,
            bind: '{theVideo.forPeople}'
        }, {
            xtype: 'combobox',
            name: 'postType',
            fieldLabel: '岗位',
            store: Common.Dic.getDicData('POST_TYPE'),
            valueField: 'value',
            displayField: 'text',
            allowBlank:false,
            bind: '{theVideo.postType}'
        }, {
            xtype: 'textareafield',
            name: 'introduction',
            fieldLabel: '简介',
            allowBlank:false,
            bind: '{theVideo.introduction}'
        }],
        buttons: [{
            text: '确定',
            handler: 'editVedioInfo'
        }, {
            text: '取消',
            handler: 'closeFormWindow'
        }]
    }
});
