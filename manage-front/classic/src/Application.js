Ext.define('Admin.Application', {
    extend: 'Ext.app.Application',

    requires: [
        'Admin.view.authentication.Login',
        'Admin.view.main.Main',
        'Common.Config',
        'Ext.container.Viewport'
    ],

    name: 'Admin',

    stores: [
        'Base',
        'NavigationTree',
        /*
         字典管理
         */
        'dictionary.Dictionary',
        'dictionary.Province',
        'dictionary.City',
        /* 用户管理 */
        'users.User',
        'users.UserForCustomer',

        /*角色管理*/
        'roles.Role',
        'users.RoleType',


        /*资源 (权限菜单)*/
        'resources.Resource',
        'resources.Menu',
        'resources.Permission',

        /*建筑公司管理*/
        'buildCompany.BuildCompany',

        /*建筑工地管理*/
        'buildSite.BuildSite',

        /*建筑工地人员管理*/
        'buildLabourer.BuildLabourer',

        /*题库管理*/
        'eduQuestionBank.EduQuestionBank',
        /*题目管理*/
        'eduQuestion.EduQuestion',
        'eduQuestion.NotSelectEduQuestion',
        'eduQuestion.VideoContentQus',

        /*答案管理*/
        'eduQuestionAnswer.EduQuestionAnswer',
        'eduQuestion.EduQuestion',

        /*视频管理*/
        'video.Video',
        'video.VideoChapter',
        'video.VideoContent',

        /*图文管理*/
        'picArticle.PicArticle',
        'picArticle.ClassifyTree',
        /*模拟考试*/
        'mockExam.MockExam',
        'mockExam.MockExamQuestions'

    ],

    // defaultToken : 'dashboard',

    // The name of the initial view to create. This class will gain a "viewport" plugin
    // if it does not extend Ext.Viewport.
    //
    // mainView: 'Admin.view.main.Main',

    launch: function () {
        Common.Config.inIt();
        var loc = 'login';
        Ext.Ajax.request({
            url: Common.Config.requestPath('System', 'Users', 'validateLogin'),
            method: 'get',
            async: false,
            success: function (response, opts) {
                var obj = Ext.decode(response.responseText);
                if (obj.code == '0' && obj.data == 'GGsimida') {
                    Common.Config.storage.setItem(Common.Config.LOGINFLAG, 'true');
                    loc = location.hash.substring(1) || 'admindashboard';
                    Ext.create('Ext.container.Viewport', {
                        items: {
                            xtype: 'main'
                        }
                    });
                } else {
                    Common.Config.storage.setItem(Common.Config.LOGINFLAG, 'false');
                    Ext.create('Ext.container.Viewport', {
                        items: {
                            xtype: 'login'
                        }
                    });
                }
            }
        });
        Ext.util.History.setHash(loc);
    },

    onAppUpdate: function () {
        Ext.Msg.confirm('应用更新', '有新版本发布, 重新加载?',
            function (choice) {
                if (choice === 'yes') {
                    window.location.reload();
                }
            }
        );
    }
});
