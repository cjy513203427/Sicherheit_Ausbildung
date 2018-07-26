/**
 * Created by CC on 2016/9/2.
 */
Ext.define('Admin.store.dictionary.Dictionary', {
    extend: 'Admin.store.Base',

    requires: [
        'Admin.model.dictionary.Dictionary'
    ],

    model: 'Admin.model.dictionary.Dictionary',

    storeId: 'dictionary.Dictionary',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('Dictionary' , 'read'),
            create: Common.Config.requestPath('Dictionary', 'create'),
            update: Common.Config.requestPath('Dictionary', 'update')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});