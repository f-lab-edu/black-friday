import http from 'k6/http';
import {check} from 'k6';
import {commonData} from "../common.js";

//k6 옵션 설정
// export const options = {
//     vus : 1,
//     duration : '1s',
// }
export const options = {
    stages: [
        // {duration :'1m', target : 50},
        // {duration :'3m', target : 100},
        // {duration :'5m', target : 300},
        {duration :'1m', target : 10},
        // {duration :'5m', target : 300},
        // {duration :'3m', target : 100},
        // {duration :'1m', target : 50}
    ],
    thresholds: {
        http_req_duration : ['avg < 1000','p(95) < 1000'], //95%가 100ms 안에 응답되도록 해보기
        http_req_failed: ['rate < 5.00'], //fail이 5% 아래여야함
    }
}

//초기값 세팅
//로그인을 먼저 처리하여 token 값을 획득한다.
export function setup() {
    // 2. setup code
    const url = commonData.process_url+'/api/member/login';
    let body =  JSON.stringify({ 'id': 'test', 'password': 'Test!234' });
    const params = {
        headers : {
            'Content-Type': 'application/json'
        }
    }
    const res = http.post(url,body,params);
    // console.log(res.headers.Authorization);
    // console.log(commonData.token);
    //
    // const res2 = http.get(commonData.process_url+"/api/product/list/v2/blackfriday?page=1");
    //
    return {
        token : res.headers.Authorization,
        // list : res2.json()
    };
}

export default function (data){
    let json = JSON.stringify(data);
    let obj = JSON.parse(json);

    commonData.token = obj.token;

    const url = commonData.process_url+'/api/product/coupon/epin/create';
    const payload = JSON.stringify({
        "idx" : 1,
        "id" : "",
        "useType" : "",
        "useStatus" : ""
    });
    const params= {
        headers : {
            'Content-Type': 'application/json',
            Authorization : `Bearer ${commonData.token}`
        }
    }
    const res = http.post(url,payload,params);
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}
