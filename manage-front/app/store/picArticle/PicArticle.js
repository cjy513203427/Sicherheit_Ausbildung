/**
 * Created by jonnyLee on 2016/9/27.
 */
Ext.define('Admin.store.picArticle.PicArticle', {
    extend: 'Admin.store.Base',

    requires: [
        'Common.Config'
    ],

    alias: 'store.picArticle',

    storeId: 'picArticle.PicArticle',

    autoLoad:false,

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('PicArticle', 'picArtilist')
            // ,delete: Common.Config.requestPath('System', 'Resources', 'delete')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});