/**
 * Created by CC on 2016/9/2.
 */
Ext.define('Admin.store.dictionary.Province', {
    extend: 'Admin.store.Base',

    storeId: 'dictionary.Province',
    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('Dictionary' , 'Province')
        },
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    }
});