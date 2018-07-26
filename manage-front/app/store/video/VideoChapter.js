/**
 * Created by Wwei on 2016/9/2.
 */
Ext.define('Admin.store.video.VideoChapter', {
    extend: 'Admin.store.Base',

    // requires: [
    //     'Admin.model.users.User'
    // ],

    // model: 'Admin.model.users.User',

    storeId: 'video.VideoChapter',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('Video', 'readChapter')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});