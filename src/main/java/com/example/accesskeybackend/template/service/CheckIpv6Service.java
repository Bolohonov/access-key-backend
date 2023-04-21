package com.example.accesskeybackend.template.service;

import java.net.SocketException;

public interface CheckIpv6Service {

    boolean checkIpv6(String siteUrl) throws SocketException;
}
