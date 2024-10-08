# frozen_string_literal: true

class ApplicationController < ActionController::API
  include ActionController::HttpAuthentication::Token::ControllerMethods

  before_action :set_current_request_details
  before_action :authenticate

  def require_sudo
    render json: { error: 'Enter your password to continue' }, status: :forbidden unless Current.session.sudo?
  end

  private

  def authenticate
    if session = authenticate_with_http_token { |token, _| Session.find_signed(token) }
      Current.session = session
    else
      request_http_token_authentication
    end
  end

  def set_current_request_details
    Current.user_agent = request.user_agent
    Current.ip_address = request.ip
  end
end
