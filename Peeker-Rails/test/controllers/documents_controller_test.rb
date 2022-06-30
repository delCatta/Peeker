# frozen_string_literal: true

require 'test_helper'

class DocumentsControllerTest < ActionDispatch::IntegrationTest
  setup do
    @document = documents(:one)
  end

  test 'should get index' do
    get documents_url, as: :json
    assert_response :success
  end

  test 'should create document' do
    assert_difference('Document.count') do
      post documents_url,
           params: { document: { description: @document.description, document_type: @document.document_type, emission_date: @document.emission_date, expiration_date: @document.expiration_date, name: @document.name, user_id: @document.user_id } }, as: :json
    end

    assert_response :created
  end

  test 'should show document' do
    get document_url(@document), as: :json
    assert_response :success
  end

  test 'should update document' do
    patch document_url(@document),
          params: { document: { description: @document.description, document_type: @document.document_type, emission_date: @document.emission_date, expiration_date: @document.expiration_date, name: @document.name, user_id: @document.user_id } }, as: :json
    assert_response :success
  end

  test 'should destroy document' do
    assert_difference('Document.count', -1) do
      delete document_url(@document), as: :json
    end

    assert_response :no_content
  end
end
