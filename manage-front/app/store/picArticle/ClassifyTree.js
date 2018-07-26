/**
 * Created by jonnyLee on 2016/9/27.
 */
Ext.define('Admin.store.picArticle.ClassifyTree', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'Common.Config'
    ],

    alias: 'store.ClassifyTree',

    storeId: 'picArticle.ClassifyTree',

    autoLoad:false,

    root: {
        id: 0,
        text: '图文分类管理'
    },

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('PicArticle', 'readTree')
            // ,delete: Common.Config.requestPath('System', 'Resources', 'delete')
        },
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});