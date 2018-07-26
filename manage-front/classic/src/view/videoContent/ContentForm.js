/**
 * Created by CC on 2016/9/1.
 */
Ext.define('Admin.view.videoContent.ContentForm', {
    extend: 'Ext.window.Window',
    xtype: 'contentForm',

    requires: [
        'Admin.view.videoContent.ContentFormController',
        'Ext.form.Panel',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Text',
        'Ext.form.field.TextArea',
        'Ext.layout.container.Fit'
    ],

    layout: 'fit',

    modal: true,
    height: 300,
    width: 400,

    controller: 'contentform',

    viewModel: {
        links: {
            theContent: {
                type: 'video.VideoContent',
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
                value:'{theContent.id}'
            }
        },{
            xtype:'combobox',
            fieldLabel:'选择集锦',
            name:'eduVideoId',
            id:'eduVideoId',
            displayField:'title',
            valueField:'id',
            bind:{
                value:'{theContent.eduVideoId}',
                disabled:'{disabled}'
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
            // ,listeners:{
            //     select:function(combo, record, index){
            //         var chapterCombo = Ext.getCmp('chapterF').form.findField('videoChapterId');
            //         chapterCombo.cleanupField();
            //         chapterCombo.clearValue();
            //         chapterCombo.getStore().addListener({
            //             'beforeload': function (store) {
            //                 //每次加载之前 scrolly to 0
            //                 Ext.apply(store.getProxy().extraParams, {eduVideoId:record.data.id});
            //                 return true;
            //             },
            //             'load': function (store) {
            //                 store.getProxy().extraParams = {};
            //             }
            //         });
            //         chapterCombo.getStore().reload({
            //             params:{
            //                 eduVideoId:record.data.id
            //             }
            //         });
            //     }
            // }
        // },{
        //     xtype:'combobox',
        //     fieldLabel:'选择章节',
        //     name:'videoChapterId',
        //     displayField:'chapterName',
        //     valueField:'id',
        //     bind:{
        //         value:'{theContent.videoChapterId}'
        //     },
        //     store:Ext.create('Ext.data.Store',{
        //         autoLoad:true,
        //         proxy: {
        //             type: 'ajax',
        //             api: {
        //                 read: Common.Config.requestPath('Video','readChapter')
        //             },
        //             reader: {
        //                 type: 'json',
        //                 rootProperty: 'data.list'
        //             }
        //         }
        //     })
        },{
            xtype: 'textfield',
            name: 'title',
            allowBlank:false,
            fieldLabel: '视频名称',
            bind: {
                value: '{theContent.title}'
            }
        }, {
            width: 300,
            xtype: 'filefield',
            name: 'file',
            msgTarget: 'side',
            // allowBlank: false,
            // anchor: '100%',
            emptyText: '上传视频',
            accept: 'audio/mp4,video/mp4',
            buttonConfig: {
                xtype: 'filebutton',
                iconCls: 'x-fa fa-cloud-upload',
                text: '选择文件'
            }
        }],
        buttons: [{
            text: '确定',
            handler: 'editContent'
        }, {
            text: '取消',
            handler: 'closeContentWindow'
        }]
    }
});
