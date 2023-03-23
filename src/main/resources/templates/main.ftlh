<html lang="ru">
<head>
    <title>db-analyzer</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Ubuntu:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap"
          rel="stylesheet">
    <title>Document</title>
</head>
<style>   body {
        background-color: #F7F9FC;
        display: flex;
        flex-direction: column;
        margin: 0;
        font-family: 'Ubuntu', sans-serif;
    }

    h1 {
        padding: 0;
        margin: 0;
    }

    p {
        padding: 0;
        margin: 0;
    }

    h2 {
        padding: 0;
        margin: 0;
    }

    h3 {
        padding: 0;
        margin: 0;
    }

    h4 {
        padding: 0;
        margin: 0;
    }

    h5 {
        padding: 0;
        margin: 0;
    }

    .text-reset {
        padding: 0;
        margin: 0;
    }

    .container {
        margin: 0 auto;
        width: 1000px;
        color: white;
    }

    .header {
        background: #181818;
        padding-top: 13px;
        padding-bottom: 13px;
    }

    .header__container {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
    }

    .header__service-name {
        font-style: normal;
        font-weight: 500;
        font-size: 16px;
    }

    .header__identity-container {
        display: flex;
        align-items: center;
        gap: 8px;
        flex-direction: row;
    }

    .header__identity-title {
        font-style: normal;
        font-weight: 400;
        font-size: 15px;
    }

    .info__container {
        margin-top: 40px;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: start;
        width: 100%;
    }

    .info__title {
        font-weight: 700;
        font-size: 28px;
        color: #181818;
        letter-spacing: 0.01em;
    }

    .info__timeline-container {
        background: #E6EBF2;
        padding: 12px 16px;
        display: flex;
        flex-direction: column;
        gap: 8px;
        border-radius: 8px;
    }

    .info__timeline-elem {
        display: flex;
        flex-direction: row;
        gap: 24px;
    }

    .info__timeline-title {
        font-weight: 700;
        font-size: 16px;
        text-align: right;

        color: #181818;
    }

    .info__timeline-time {
        font-weight: 400;
        font-size: 16px;
        line-height: 18px;

        color: #767676;
    }

    .tables__container {
        display: flex;
        flex-direction: column;
        margin-top: 64px;
    }

    .module__title {
        font-style: normal;
        font-weight: 500;
        font-size: 16px;
        line-height: 18px;
        margin-bottom: 12px;
        color: rgba(69, 84, 107, 0.6);
    }

    .tables__list-container {
        display: flex;
        flex-direction: column;
        gap: 16px;
        margin-bottom: 32px;
    }

    .table__container {
        display: flex;
        flex-direction: column;
    }

    .table__preview-container {
        background: #FFFFFF;
        border: 1px solid #DBE1E9;
        box-shadow: 0px 0px 8px rgba(118, 130, 152, 0.08);
        border-radius: 8px;
        padding: 8px;
        width: inherit;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        cursor: pointer;
    }

    .table__preview-title-container {
        background: #DBE1E9;
        border-radius: 8px;
        padding: 8px 16px;
    }

    .table__preview-title {
        font-weight: 700;
        font-size: 18px;
        line-height: 21px;
        text-transform: uppercase;
        color: #45546B;
    }

    .table__content-container {
        flex-direction: column;
        gap: 8px;
        display: none;
        margin-left: 32px;
        margin-top: 12px;
    }

    .query__container {
        background: #FFFFFF;
        border: 2px solid #d31414;
        box-shadow: 0px 0px 8px rgba(118, 130, 152, 0.08);
        border-radius: 8px;
        padding: 0 16px;
        width: inherit;
    }

    .query__container-success {
        background: #FFFFFF;
        border: 2px solid #23D314;
        box-shadow: 0px 0px 8px rgba(118, 130, 152, 0.08);
        border-radius: 8px;
        padding: 0 16px;
        width: inherit;
    }

    .query__preview-container {
        cursor: pointer;
        display: flex;
        flex-direction: row;
        align-items: end;
        justify-content: space-between;
        padding-bottom: 14px;
        padding-top: 14px;
    }

    .query__query-text {
        font-weight: 400;
        font-size: 16px;
        line-height: 140%;
        color: #505C70;
    }

    .query__show-button {
        background: #181818;
        border-radius: 80px;
        padding: 7px 14px 8px 14px;
        color: white;
        font-weight: 700;
        cursor: pointer;
        font-size: 15px;
        letter-spacing: 0.02em;
        border: none;
    }

    .query__content-container {
        display: none;
        flex-direction: column;
        gap: 24px;
    }

    .query__top-module-container {
        display: flex;
        flex-direction: row;
    }

    .query__top-module-left-container {
        display: flex;
        flex-direction: column;
        flex-shrink: 0;
        width: 60%;
        padding-right: 16px;
    }

    .query__top-module-paragraph {
        font-style: normal;
        font-weight: 500;
        font-size: 16px;
        line-height: 135%;
        color: #181818;
        margin-top: 14px;
    }

    .query__content-error-container {
        background: #FDD9D9;
        border-radius: 8px;
        padding: 12px 16px;
    }

    .query__content-error-text {
        font-weight: 700;
        font-size: 16px;
        line-height: 135%;
        color: #4F0E0E;
    }

    .query-fields__container {
        background: #EBEBEB;
        border-radius: 8px;
        width: 100%;
        display: flex;
        flex-direction: column;
        gap: 4px;
        padding: 10px;
    }

    .query-field__container {
        display: flex;
        flex-direction: row;
        align-items: center;
        position: relative;
        gap: 12px;
    }

    .query-field__value {
        font-style: normal;
        font-weight: 700;
        font-size: 16px;
        line-height: 135%;
        color: #4F0E0E;
    }

    .query-field__key-container {
        background: #FFFFFF;
        border: 1px solid #C4C4C4;
        border-radius: 8px;
        cursor: pointer;
        padding: 4px 10px;
    }

    .query-field__description-container {
        position: absolute;
        top: 32px;
        left: 0;
        width: 300px;
        background: white;
        border: 1px solid #C4C4C4;
        box-shadow: 0px 0px 12px rgba(0, 0, 0, 0.1);
        border-radius: 8px;
        z-index: 300;
        display: none;
        padding: 12px 16px;
    }

    .query-field__description-text {
        font-style: normal;
        font-weight: 400;
        font-size: 15px;
        line-height: 140%;
        color: #4F0E0E;
    }

    .query-field__key {
        font-style: normal;
        font-weight: 500;
        font-size: 16px;
        line-height: 135%;
        color: #4F0E0E;
    }

    .query__content-module-container {
        border-top: 1px solid #DBE1E9;
        padding-top: 16px;
        padding-bottom: 12px;
        display: flex;
        flex-direction: column;
    }

    .query__content-short-stacktrace-text {
        font-style: italic;
        font-weight: 500;
        font-size: 16px;
        line-height: 145%;
        color: #1044A8;
    }

    .full-stacktrace__container {
        width: auto;
        overflow-y: scroll;
        padding: 12px 20px;
        overflow-x: scroll;
        height: 200px;
        background: #F8F8F8;
        border-radius: 8px;
    }

    .full-stacktrace__text {
        font-style: italic;
        font-weight: 500;
        font-size: 13px;
        line-height: 140%;
        color: #1044A8;
    }
</style>
<body>
<header class="header">
    <div class="container header__container">
        <h3 class="text-reset header__service-name">
            db-analyzer
        </h3>
        <div class="header__identity-container">
            <svg width="20" height="20" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
                <g clip-path="url(#clip0_446_68)">
                    <mask id="mask0_446_68" style="mask-type:luminance" maskUnits="userSpaceOnUse" x="0" y="0"
                          width="20" height="20">
                        <path d="M20 0H0V19.7818H20V0Z" fill="white"/>
                    </mask>
                    <g mask="url(#mask0_446_68)">
                        <path d="M9.82731 6.10581L2.64205 2.7864C2.49392 2.71809 2.32877 2.69417 2.16707 2.7176C1.6584 2.78811 1.19232 3.28706 1.19133 3.89277L1.16333 18.771C1.16333 19.3845 1.63364 19.7454 2.13733 19.6754C2.32945 19.6487 2.52646 19.5593 2.70394 19.398L8.76962 13.8794L6.14191 12.6654C5.53081 12.3838 5.2449 11.8013 5.21666 11.2539C5.18854 10.7081 5.40399 10.13 5.86113 9.71414L9.82731 6.10581Z"
                              fill="url(#paint0_radial_446_68)"/>
                        <path d="M17.791 0.106593C18.2955 0.0365732 18.7662 0.397444 18.765 1.01098L18.7373 15.8892C18.736 16.4944 18.2699 16.9934 17.7616 17.0644C17.5998 17.0878 17.4347 17.0639 17.2865 16.9956L6.45855 11.9931C5.83974 11.7079 5.79272 10.7841 6.3687 10.2602L17.2244 0.383979C17.4021 0.222641 17.5989 0.133279 17.791 0.106593Z"
                              fill="url(#paint1_radial_446_68)"/>
                    </g>
                </g>
                <defs>
                    <radialGradient id="paint0_radial_446_68" cx="0" cy="0" r="1" gradientUnits="userSpaceOnUse"
                                    gradientTransform="translate(27.1154 6.70844) rotate(173.014) scale(26.0967 9.96249)">
                        <stop stop-color="#E02E2E"/>
                        <stop offset="1" stop-color="#EE215B"/>
                    </radialGradient>
                    <radialGradient id="paint1_radial_446_68" cx="0" cy="0" r="1" gradientUnits="userSpaceOnUse"
                                    gradientTransform="translate(1.12644 12.9664) rotate(-9.87552) scale(17.8827 10.1301)">
                        <stop stop-color="#0091FF"/>
                        <stop offset="1" stop-color="#1846C7"/>
                    </radialGradient>
                    <clipPath id="clip0_446_68">
                        <rect width="20" height="19.7818" fill="white"/>
                    </clipPath>
                </defs>
            </svg>
            <h4 class="text-reset header__identity-title">
                286535
            </h4>
        </div>
    </div>
</header>
<div class="container">
    <section class="info__container">
        <h3 class="info__title">
            Queries report
        </h3>
        <div class="info__timeline-container">
            <div class="info__timeline-elem">
                <h5 class="info__timeline-title">from</h5>
                <p class="info__timeline-time">${from}</p>
            </div>
            <div class="info__timeline-elem">
                <h5 class="info__timeline-title">to</h5>
                <p class="info__timeline-time">${to}</p>
            </div>
        </div>
    </section>

    <section class="tables__container">
        <h5 class="module__title">
            Tables
        </h5>
        <div class="tables__list-container" id="tables">
            <#list tables as name, value>
                <div class="table__container">
                    <div class="table__preview-container" id=${name}>
                        <div class="table__preview-title-container">
                            <h3 class="table__preview-title">
                                ${name}
                            </h3>
                        </div>
                    </div>
                    <div class="table__content-container" id="queries">
                        <#list value.queryExplainResult as query, res>
                        <#--Error query-->
                            <#if res.criticalTime < res.executionTime>
                                <div class="query__container">
                                    <div class="query__preview-container" id="${query}">
                                        <p class="query__query-text">
                                            ${query}
                                        </p>
                                        <button class="query__show-button">
                                            explain
                                        </button>
                                    </div>
                                    <div class="query__content-container">
                                        <div class="query__content-module-container">

                                            <div class="query__top-module-container">
                                                <div class="query__top-module-left-container">
                                                    <div class="query__content-error-container">
                                                        <p class="query__content-error-text">
                                                            Your request did not pass the time limit=${res.criticalTime}
                                                            ms<br/>
                                                            Real execution time&#8776;${res.executionTime} ms<br/>
                                                            <br/></p>
                                                    </div>
                                                    <p class="query__top-module-paragraph">
                                                        <#list res.advice as element> ${element}<br/></#list>
                                                    </p>
                                                </div>
                                                <div class="query-fields__container">
                                                    <#--Startup Cost-->
                                                    <#if (res.plan.startupCost)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Startup Cost
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${res.plan.startupCost}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Blocks on start
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#--Total Cost-->
                                                    <#if (res.plan.totalCost)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Total Cost
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.totalCost)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Full block count
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#--Plan Rows-->
                                                    <#if (res.plan.planRows)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Plan Rows
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.planRows)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Plan Rows
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#--Plan Width-->
                                                    <#if (res.plan.planWidth)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Plan Width
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.planWidth)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Plan Width
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#if res.plan.sharedHitBlocks! != 0 || res.plan.sharedReadBlocks! != 0>
                                                    <#--Shared Hit Blocks-->
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Shared Hit Blocks
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.sharedHitBlocks)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Shared Hit Blocks
                                                                </p>
                                                            </div>
                                                        </div>
                                                    <#--Shared Read Blocks-->
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Shared Read Blocks
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.sharedReadBlocks)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Shared Read Blocks
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="query__content-module-container">
                                            <h5 class="module__title">
                                                Module Stacktrace
                                            </h5>
                                            <p class="query__content-short-stacktrace-text">
                                                <#list res.moduleStackTrace as element> ${element}<br/></#list>
                                            </p>
                                        </div>
                                        <div class="query__content-module-container">
                                            <h5 class="module__title">
                                                Full Stacktrace
                                            </h5>
                                            <div class="full-stacktrace__container">
                                                <p class="full-stacktrace__text">
                                                    <#list res.baseStackTrace as element> ${element}<br/></#list>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#if>

                        <#--Success query-->
                            <#if res.executionTime <= res.criticalTime>
                                <div class="query__container-success">
                                    <div class="query__preview-container" id="${query}">
                                        <p class="query__query-text">
                                            ${query}
                                        </p>
                                        <button class="query__show-button">
                                            explain
                                        </button>
                                    </div>
                                    <div class="query__content-container">
                                        <div class="query__content-module-container">

                                            <div class="query__top-module-container">
                                                <div class="query-fields__container">
                                                    <#--Startup Cost-->
                                                    <#if (res.plan.startupCost)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Startup Cost
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.startupCost)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Blocks on start
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#--Total Cost-->
                                                    <#if (res.plan.totalCost)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Total Cost
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.totalCost)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Full block count
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#--Plan Rows-->
                                                    <#if (res.plan.planRows)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Plan Rows
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.planRows)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Plan Rows
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#--Plan Width-->
                                                    <#if (res.plan.planWidth)??>
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Plan Width
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.planWidth)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Plan Width
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                    <#if res.plan.sharedHitBlocks != 0 || res.plan.sharedReadBlocks != 0>
                                                    <#--Shared Hit Blocks-->
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Shared Hit Blocks
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.sharedHitBlocks)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Shared Hit Blocks
                                                                </p>
                                                            </div>
                                                        </div>
                                                    <#--Shared Read Blocks-->
                                                        <div class="query-field__container">
                                                            <div class="query-field__key-container">
                                                                <p class="query-field__key">
                                                                    Shared Read Blocks
                                                                </p>
                                                            </div>
                                                            <p class="query-field__value">
                                                                =
                                                            </p>
                                                            <p class="query-field__value">
                                                                ${(res.plan.sharedReadBlocks)!}
                                                            </p>
                                                            <div class="query-field__description-container"
                                                                 id="description">
                                                                <p class="query-field__description-text">
                                                                    Shared Read Blocks
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </#if>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="query__content-module-container">
                                            <h5 class="module__title">
                                                Module Stacktrace
                                            </h5>
                                            <p class="query__content-short-stacktrace-text">
                                                <#list res.moduleStackTrace as element> ${element}<br/></#list>
                                            </p>
                                        </div>
                                        <div class="query__content-module-container">
                                            <h5 class="module__title">
                                                Full Stacktrace
                                            </h5>
                                            <div class="full-stacktrace__container">
                                                <p class="full-stacktrace__text">
                                                    <#list res.baseStackTrace as element> ${element}<br/></#list>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                        </#list>
                    </div>
                </div>
            </#list>
        </div>
    </section>
</div>
</body>
<script>
    // tables
    function onTableClick(event) {
        const elemId = event.currentTarget.getAttribute('id')
        const elem = document.getElementById(elemId)

        const style = elem.nextElementSibling.style.display

        elem.nextElementSibling.style.display = style === 'none' ? ' flex' : 'none'
    }

    tablesList = document.getElementById('tables')
    for (let child of tablesList.children) {
        const elemId = child.children[0].getAttribute('id')
        const elem = document.getElementById(elemId)
        elem.nextElementSibling.style.display = 'none'

        child.children[0].addEventListener('click', onTableClick)
    }

    // queries
    function onQueryClick() {
        const elemId = event.currentTarget.getAttribute('id')
        const elem = document.getElementById(elemId)

        const style = elem.nextElementSibling.style.display

        if (style === 'none') {
            elem.nextElementSibling.style.display = 'flex'
            elem.children[1].textContent = 'hide'
        } else {
            elem.nextElementSibling.style.display = 'none'
            elem.children[1].textContent = 'explain'
        }
    }

    const queries = document.querySelectorAll('#queries')
    for (let child of queries) {
        for (let query of child.children) {
            const elemId = query.children[0].getAttribute('id')
            const elem = document.getElementById(elemId)
            elem.nextElementSibling.style.display = 'none'
            elem.children[1].textContent = 'explain'

            query.children[0].addEventListener('click', onQueryClick)
        }
    }

    // fields
    const fields = document.querySelectorAll(".query-field__key-container")

    for (let field of fields) {
        field.addEventListener('mouseover', (event) => {
            event.currentTarget.parentNode.querySelector('#description').style.display = 'flex'
        })

        field.addEventListener('mouseout', (event) => {
            event.currentTarget.parentNode.querySelector('#description').style.display = 'none'
        })
    }

</script>
</html>