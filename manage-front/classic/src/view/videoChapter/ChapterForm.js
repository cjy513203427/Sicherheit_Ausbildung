/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.videoChapter.ChapterForm', {
    extend: 'Ext.window.Window',
    xtype: 'chapterForm',

    requires: [
        'Admin.view.videoChapter.ChapterFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 200,
    width: 400,

    controller: 'chapterform',

    viewModel: {
        links: {
            theChapter: {
                type: 'video.VideoChapter',
                create: true
            }
        },
        data: {
            roleComboQueryMode: 'remote'
        }
    },

    items: {
        xtype: 'form',
        id:'chapterF',
        modelValidation: true,
        defaults: {
            labelAlign: 'left',
            margin: 10,
            msgTarget: 'side'
        },
        items: [{
            xtype:'hidden',
            name:'id',
            // id :'hiddenDicId',
            bind:{
                value:'{theChapter.id}'
            }
        },{
            xtype:'combobox',
            anchor: '95%',
            fieldLabel:'选择集锦',
            name:'eduVideoId',
            displayField:'title',
            valueField:'id',
            bind:{
                value:'{theChapter.eduVideoId}'
            },
            store:Ext.create('Ext.data.Store',{
                autoLoad:true,
                proxy: {
                    type: 'ajax',
                    api: {
                        read: Common.Config.requestPath('Video','read')
                    },
                    reader: {
                        type: 'json',
                        rootProperty: 'data.list'
                    }
                }
            })
        },{
            xtype: 'textfield',
            anchor: '95%',
            name: 'chapterName',
            allowBlank:false,
            fieldLabel: '章节名称',
            bind: {
                value: '{theChapter.chapterName}'
            }
        }],
        buttons: [{
            text: '确定',
            handler: 'editChapterInfo'
        }, {
            text: '取消',
            handler: 'closeChapterWindow'
        }]
    }
});
