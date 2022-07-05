require "test_helper"

class Tags::DocumentsControllerTest < ActionDispatch::IntegrationTest
  test "should get index" do
    get tags_documents_index_url
    assert_response :success
  end
end
