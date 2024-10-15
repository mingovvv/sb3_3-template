package demo.template.sb3_3template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class Test1 {

    @Test
    void test() throws JsonProcessingException {

        String jsonData = """
                {
                  "status": true,
                  "code": "S200",
                  "message": "Success.",
                  "calls": [
                    {
                      "widgetHistId": 1,
                      "isLiked": true,
                      "isEvaluated": true
                    },
                    {
                      "widgetHistId": 2,
                      "isLiked": true,
                      "isEvaluated": true
                    },
                    {
                      "widgetHistId": 4,
                      "isLiked": true,
                      "isEvaluated": true
                    }
                  ],
                  "data": {
                    "widgetGroupNo": 1,
                    "entities": [
                      "삼성전자"
                    ],
                    "widgets": [
                      {
                        "widgetNo": null,
                        "template": null,
                        "values": null,
                        "supplement": {
                          "type": "news",
                          "news": {
                            "summary": "asd",
                            "newsEntities": [
                              {
                                "title": "제목",
                                "url": "경로",
                                "newDt": "yyyyMMddHHmmss",
                                "source": "발행처"
                              }
                            ]
                          }
                        }
                      },
                      {
                        "widgetNo": 5,
                        "template": "최근 {} 관련 주요 뉴스는 {} {} 이에요. 비슷한 뉴스가 있었을 때, {} 주가는 평균적으로 다음날까지 {}% 올랐고, {}보다 {}% {} 성과를 보였어요.",
                        "values": [
                          {
                            "text": "삼성전자",
                            "style": "strong good"
                          },
                          {
                            "text": "2023년 6월 12일",
                            "style": "strong good"
                          },
                          {
                            "text": "유럽 경기 부양책",
                            "style": "strong good"
                          },
                          {
                            "text": "삼성전자",
                            "style": "strong good"
                          },
                          {
                            "text": "3.2",
                            "style": "strong good"
                          },
                          {
                            "text": "코스피",
                            "style": "strong good"
                          },
                          {
                            "text": "1.1",
                            "style": "strong good"
                          },
                          {
                            "text": "좋은",
                            "style": "strong good"
                          }
                        ],
                        "supplement": null
                      },
                      {
                        "widgetNo": 6,
                        "template": "지금까지 뉴스를 분석해보면, {}의 주가는 아래 이벤트가 중요해요.|{}주가는 {}에 가장 많이 올랐고, {}에 가장 많이 내렸어요.",
                        "values": [
                          {
                            "text": "삼성전자",
                            "style": "strong good"
                          },
                          {
                            "text": "삼성전자",
                            "style": "strong good"
                          },
                          {
                            "text": "유럽경기 부양책",
                            "style": "strong good"
                          },
                          {
                            "text": "미국 신용등급 강등 사태",
                            "style": "strong good"
                          }
                        ],
                        "supplement": {
                          "type": [
                            "box"
                          ],
                          "box": {
                            "positive": [
                              "유럽 경기 부양책",
                              "BOK 완화",
                              "유럽"
                            ],
                            "negative": [
                              "미국 신용등급 상등 사태",
                              "G2 갈등 완호",
                              "미국 정부의 구제정책"
                            ]
                          }
                        }
                      },
                      {
                        "widgetNo": 7,
                        "template": "최근 1개월간 {}의 수익률을 분석해본 결과 시장에서 {} 수익률을 보이는 주요 스타일과 {}해요. {} 주가 변화는 국내 시장 분위기에 영향을 많이 {}.|{}의 {} {}, {} {} 스타일이최근 1개월 수익에 긍정적인 영향을 미치고 있는 중이에요. 반면, {} {}, {} {} 스타일은 부정적으로 작용하고 있어요.",
                        "values": [
                          {
                            "text": "삼성전자",
                            "style": "strong good"
                          },
                          {
                            "text": "높은",
                            "style": "strong good"
                          },
                          {
                            "text": "일치",
                            "style": "strong good"
                          },
                          {
                            "text": "삼성전자",
                            "style": "strong good"
                          },
                          {
                            "text": "받았어요",
                            "style": "strong good"
                          },
                          {
                            "text": "삼성전자",
                            "style": "strong good"
                          },
                          {
                            "text": "높은",
                            "style": "strong good"
                          },
                          {
                            "text": "배당",
                            "style": "strong good"
                          },
                          {
                            "text": "낮은",
                            "style": "strong good"
                          },
                          {
                            "text": "성장",
                            "style": "strong good"
                          },
                          {
                            "text": "높은",
                            "style": "strong good"
                          },
                          {
                            "text": "주가변동",
                            "style": "strong good"
                          },
                          {
                            "text": "높은",
                            "style": "strong good"
                          },
                          {
                            "text": "기업규모 스타일일",
                            "style": "strong good"
                          }
                        ],
                        "supplement": {
                          "type": [
                            "radarChart",
                            "table"
                          ],
                          "radarChart": {
                            "factors": [
                              "배당",
                              "수익성",
                              "성장",
                              "주가 수익률",
                              "가치"
                            ],
                            "series": [
                              {
                                "category": "market",
                                "displayName": "주식시장",
                                "values": [
                                  70,
                                  60,
                                  80,
                                  50,
                                  65
                                ]
                              },
                              {
                                "category": "stock",
                                "displayName": "삼성전자",
                                "values": [
                                  60,
                                  50,
                                  70,
                                  45,
                                  55
                                ]
                              }
                            ]
                          },
                          "table": {
                            "headers": [
                              "스타일",
                              "1개월 수익 기여도",
                              "삼성전자 노출도"
                            ],
                            "rows": [
                              [
                                "배당",
                                "+0.0",
                                "0.00"
                              ],
                              [
                                "성장",
                                "+0.0",
                                "0.00"
                              ],
                              [
                                "주가변동",
                                "+0.0",
                                "-0.00"
                              ],
                              [
                                "팩터명",
                                "+0.0",
                                "-0.00"
                              ]
                            ]
                          }
                        }
                      }
                    ]
                  }
                }
                """;

        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 데이터를 JsonNode로 파싱
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode widgetsNode = rootNode.path("data").path("widgets");

        // widgets 리스트의 각 요소에 새로운 필드 추가
        if (widgetsNode.isArray()) {
            Iterator<JsonNode> widgetsIterator = widgetsNode.elements();
            int widgetHistIdCounter = 1; // widgetHistId 값은 필요에 따라 증가시키거나 고정할 수 있습니다.

            while (widgetsIterator.hasNext()) {
                ObjectNode widget = (ObjectNode) widgetsIterator.next();

                // 위젯 callId
                JsonNode widgetNoNode = widget.get("widgetHistNo");
                int widgetHistNo = widgetNoNode.asInt();

                // 새 필드 추가
                widget.put("widgetHistId", widgetHistIdCounter++);
                widget.put("isLiked", true);
                widget.put("isEvaluated", true);
            }
        }

        // 수정된 JSON 데이터를 다시 String으로 변환
        String updatedJsonData = objectMapper.writeValueAsString(rootNode);
        System.out.println(updatedJsonData);

    }



}
