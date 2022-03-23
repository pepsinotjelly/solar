export function queryItemInfo() {
    return fetch('/api/itemInfo').then(res=> res.json());
}