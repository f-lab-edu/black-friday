import http from 'k6/http';
import {check, sleep} from 'k6';
import {commonData} from "../common.js";

//k6 옵션 설정
export const options = {
    stages: [
        // {duration :'5m', target : 5},
        // {duration :'10m', target : 5},
        {duration :'15m', target : 50},
        {duration :'3m', target : 5},
        {duration :'15m', target : 50},
        // {duration :'20m', target : 10},
        // {duration :'15m', target : 8},
        // {duration :'10m', target : 5},
        // {duration :'5m', target : 3},
    ],
    thresholds: {
        http_req_duration : ['p(95) < 100'] //95%가 100ms 안에 응답되도록 해보기
    }
}
export default function (){
   const res = http.get("http://localhost:8080/api/product/list/blackfriday");
    check(res, {
        'is status 200': (r) => r.status === 200,
    });
}