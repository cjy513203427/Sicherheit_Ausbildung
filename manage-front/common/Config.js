Ext.define('Common.Config', {

    xtype: 'config',

    requires: [
        'Ext.util.LocalStorage',
        'Common.util.Util'
    ],

    statics: {
        ajaxTimeout: 15 * 60 * 1000, // ajax请求超时事件 minute * 60 * 1000
        user: {}, //存储用户信息的全局变量

        storage: new Ext.util.LocalStorage({id: 'financial'}),      //localstorage

        LOGINFLAG: 'loginFlag',

        _dev_serverUrl: 'http://127.0.0.1:8004/rest/',

        _serverUrl: 'http://yun.yyhvr.cn/rest/',

        _test_serverUrl: 'http://127.0.0.1:8005/rest/',

        imageAddressUrl: 'http://oss.yyhvr.cn/',

        /*******业务参数   配置 *******/
        success_code: '0',        //成功返回码
        pageSize: 15, // 分页大小设定
        sourceType: 'YYH-financial',      //暂时没有使用
        business_name: '云艺化安全培训后台管理系统',//企业名称
        business_css: 'background-color:white;color: #4E4E4E;font-size: 22px',
        business_image_css: 'width:65px;height:53px;',
        serverPaths: {
            /*  安全 */
            Security: {
                Authentic: {
                    login: 'index/login',     //登录
                    logout: 'index/logout',     //退出
                    menuList: 'authentic/menuList'
                },
                Permission: {
                    read: 'security/permission/list'
                },
                Menu: {
                    read: 'security/menu/list'
                },
                Role: {
                    read: '~api/user/role'
                }
            },

            Statistics: {
                dashboard: 'statistics/dashboard',
                monthChart: 'statistics/monthChart'
            },

            Dictionary: {
                read: 'dictionary/list',
                create: 'dictionary/create',
                update: 'dictionary/update',
                getDicListByCode: 'dictionary/getDicInfo',
                savePreInfoDic: 'dictionary/savePreInfoDic',
                Province: 'dictionary/provinceList',
                City: 'dictionary/cityList',
            },
            Img: {
                Chart: {
                    download: 'img/chart/download'
                }
            },
            /*视频管理*/
            Video: {
                read: 'video/videoInfo',
                insert: 'video/addVideoInfo',
                update: 'video/updateVideoInfo',
                readChapter: 'videoChapter/queryChapterList',
                insertChapter: 'videoChapter/addChapter',
                updateChapter: 'videoChapter/updateChapter',
                readContent: 'chapterContent/queryContentList',
                insertContent: 'chapterContent/addChapterContent',
                delete: 'chapterContent/delete',
                deleteChapter: 'videoChapter/deleteChapter',
                deleteVideoCollection: 'video/deleteVideoCollection',
                updateContent: 'chapterContent/updateContent'
            },
            /*图文管理*/
            PicArticle: {
                readTree: 'imgtxtClassify/treelist',
                addClassify: 'imgtxtClassify/addClassify',
                updateClassify: 'imgtxtClassify/updateClassify',
                deleteClassify: 'imgtxtClassify/deleteClassify',
                ueUpload: 'imgtext/ueUploadPic',
                addPicArticle: 'imgtext/addPicArticle',
                addPdfPicArticle: 'imgtext/addPdfPicArticle',
                updateHTML: 'imgtext/updateHTML',
                updatePDF: 'imgtext/updatePDF',
                delPicArticle: 'imgtext/delPicArticle',
                uploadLitimg: 'imgtext/uploadLitimg',
                picArtilist: 'imgtext/list'
            },
            /*  系统管理 */
            System: {
                Roles: {
                    read: 'role/list',
                    create: 'role/create',
                    update: 'role/update',
                    delete: 'role/delete',
                    active: 'role/active'
                },
                Users: {
                    read: 'user/queryUserList',
                    create: 'user/saveUser',
                    update: 'user/modifyUser',
                    disable: 'user/disable',
                    active: 'user/active',
                    info: 'user/getUserInfo',
                    resetpassword: 'user/resetpassword',
                    modifypassword: 'user/modifypassword',
                    getUserRole: 'user/getUserRole',
                    updateUserRole: 'user/grantAuthorization',
                    getWeather: 'user/getWeather',
                    queryUserByRole: 'user/queryUserByRole',
                    validateLogin: 'user/validateLogin'
                },
                Resources: {
                    treelist: 'resource/treelist',
                    menu: 'resource/menu',
                    button: 'resource/button',
                    insert: 'resource/insert',
                    update: 'resource/update',
                    delete: 'resource/delete'
                }
            },
            EduQuestionBank: {
                queryEduQuestionBank: 'eduQuestionBank/queryEduQuestionBank',
                modifyEduQuestionBank: 'eduQuestionBank/modifyEduQuestionBank',
                addEduQuestionBank: 'eduQuestionBank/addEduQuestionBank',
                deleteEduQuestionBank: 'eduQuestionBank/deleteEduQuestionBank'
            },
            EduQuestion: {
                queryEduQuestion: 'eduQuestion/queryEduQuestion',
                addEduQuestion: 'eduQuestion/addEduQuestion',
                modifyEduQuestion: 'eduQuestion/modifyEduQuestion',
                deleteEduQuestion: 'eduQuestion/deleteEduQuestion',
                queryVideoContentQuestion: 'eduQuestion/queryVideoContentQuestion',
                createVideoQus:'eduQuestion/createVideoQus',
                queryVideoContentQuestion:'eduQuestion/queryVideoContentQuestion',
                importExcelForEduQuestion: 'eduQuestion/importExcelForEduQuestion',
                importContentQuestion:'eduQuestion/importContentQuestion',
                queryNotSelectEduQuestion: 'eduQuestion/queryNotSelectEduQuestion',
                uploadVideo:'eduQuestion/uploadVideo'
            },
            /*建筑公司管理*/
            BuildCompany: {
                read: 'buildCompany/queryBuildCompany',
                create: 'buildCompany/createBuildCompany',
                update: 'buildCompany/updateBuildCompany',
                enOrDisBuildCompany: 'buildCompany/enOrDisBuildCompany'
            },
            /*建筑工地管理*/
            BuildSite: {
                read: 'buildSite/queryBuildSiteList',
                create: 'buildSite/createBuildSite',
                update: 'buildSite/updateBuildSite',
                enOrDisBuildSite: 'buildSite/enOrDisBuildSite'
            },
            /*建筑工地人员管理*/
            BuildLabourer: {
                read: 'buildLabourer/queryBuildLabourer',
                create: 'buildLabourer/createBuildLabourer',
                update: 'buildLabourer/updateBuildLabourer',
                enOrDisBuildLabourer: 'buildLabourer/enOrDisBuildLabourer'
            },
            /* 模拟考试 */
            MockExam: {
                read: 'exam/queryExamMockList',
                queryThisMockQuestions: 'exam/queryThisMockQuestions',
                addExamQuestions: 'exam/addExamQuestions',
                create: 'exam/createExamMock',
                update: 'exam/updateExamMock',
                deleteExamMock: 'exam/deleteExamMock',
                deleteMockDownQuestion: 'exam/deleteMockDownQuestion'
            },
            EduQuestionAnswer: {
                queryEduQuestionAnswer: 'eduQuestionAnswer/queryEduQuestionAnswer',
                modifyEduQuestionAnswer: 'eduQuestionAnswer/modifyEduQuestionAnswer',
                addEduQuestionAnswer: 'eduQuestionAnswer/addEduQuestionAnswer',
                deleteEduQuestionAnswer: 'eduQuestionAnswer/deleteEduQuestionAnswer'
            }
        },

        /**
         * request path config
         */
        requestPath: function (module, cmodule, method) {
            var path = null;
            try {
                if (arguments.length === 2) {
                    path = Common.Config.serverPaths[module][cmodule];
                } else if (arguments.length === 3) {
                    path = Common.Config.serverPaths[module][cmodule][method];
                } else {
                    console.error(Ext.String.format("you should sepcial module={0}, [cmodule={1}] method={2} in config.js ", module, cmodule, method));
                }
            } catch (ex) {
                console.error(Ext.String.format("you should sepcial module={0}, [cmodule={1}] method={2} in config.js ", module, cmodule, method));
            }
            if (!path) {
                console.error(Ext.String.format('error Path: {0},  module={1}, cmodule={2}, method={3}  ' + path, module, cmodule, method));
            }
            if (Common.util.Util.env() === 'dev') {
                return Common.Config._dev_serverUrl + path;
            } else if (Common.util.Util.env() === 'test') {
                return Common.Config._test_serverUrl + path;
            } else {
                return Common.Config._serverUrl + path;
            }
        },


        // app初始化执行
        inIt: function () {
            Common.util.Util.inIt();
        }
    }
});