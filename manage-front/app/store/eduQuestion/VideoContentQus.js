/**
 * Created by IntelliJ IDEA.
 * User: Joanne
 * Date: 2018/6/28
 * Time: 11:14
 */
Ext.define('Admin.store.eduQuestion.VideoContentQus',{

    extend: 'Admin.store.Base',

    // requires: [
    //     'Admin.model.eduQuestion.EduQuestion'
    // ],
    //
    // model: 'Admin.model.eduQuestion.EduQuestion',

    storeId: 'eduQuestion.VideoContentQus',

    proxy: {
        type: 'ajax',
        api: {
            read: Common.Config.requestPath('EduQuestion' , 'queryVideoContentQuestion')
        },
        reader: {
            type: 'json',
            rootProperty: 'data.list',
            totalProperty: 'data.total'
        }
    }
});