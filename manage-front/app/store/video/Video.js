/**
 * Created by Wwei on 2016/9/2.
 */
Ext.define('Admin.store.video.Video', {
    extend: 'Admin.store.Base',

    storeId: 'video.Video',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('Video', 'read')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});